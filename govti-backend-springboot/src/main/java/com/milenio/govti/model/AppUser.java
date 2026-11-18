package com.milenio.govti.model;

import jakarta.persistence.*;

@Entity
@Table(name="usuarios")
public class AppUser {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false, unique=true) private String username;
    @Column(nullable=false) private String password;
    @Column(nullable=false) private String nombre;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private Role role;

    public AppUser() {}
    public AppUser(String username, String password, String nombre, Role role) {
        this.username=username; this.password=password; this.nombre=nombre; this.role=role;
    }
    public Long getId(){return id;} public String getUsername(){return username;}
    public void setUsername(String v){username=v;} public String getPassword(){return password;}
    public void setPassword(String v){password=v;} public String getNombre(){return nombre;}
    public void setNombre(String v){nombre=v;} public Role getRole(){return role;}
    public void setRole(Role v){role=v;}
}
