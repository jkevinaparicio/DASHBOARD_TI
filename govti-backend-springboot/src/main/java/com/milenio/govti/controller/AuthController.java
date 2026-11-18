package com.milenio.govti.controller;

import com.milenio.govti.model.*;
import com.milenio.govti.repository.UserRepository;
import com.milenio.govti.security.JwtService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AuthController {
    private final UserRepository users; private final PasswordEncoder encoder;
    private final AuthenticationManager authManager; private final JwtService jwt;
    public AuthController(UserRepository users,PasswordEncoder encoder,AuthenticationManager authManager,JwtService jwt){
        this.users=users;this.encoder=encoder;this.authManager=authManager;this.jwt=jwt;
    }
    public record RegisterRequest(@NotBlank String username,@NotBlank String password,@NotBlank String nombre,Role role){}
    public record LoginRequest(@NotBlank String username,@NotBlank String password){}
    public record AuthResponse(String token,String username,String nombre,String role){}
    @PostMapping("/register") public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r){
        if(users.existsByUsername(r.username())) return ResponseEntity.badRequest().body("El usuario ya existe");
        Role role=r.role()==null?Role.RESPONSABLE_TI:r.role();
        // El registro público nunca debe permitir crear ADMIN.
        if(role==Role.ADMIN || role==Role.CIO) return ResponseEntity.status(403).body("Rol no permitido en registro público");
        AppUser u=users.save(new AppUser(r.username(),encoder.encode(r.password()),r.nombre(),role));
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado: "+u.getUsername());
    }
    @PostMapping("/login") public AuthResponse login(@Valid @RequestBody LoginRequest r){
        authManager.authenticate(new UsernamePasswordAuthenticationToken(r.username(),r.password()));
        AppUser u=users.findByUsername(r.username()).orElseThrow();
        return new AuthResponse(jwt.generate(u.getUsername(),u.getRole().name()),u.getUsername(),u.getNombre(),u.getRole().name());
    }
}
