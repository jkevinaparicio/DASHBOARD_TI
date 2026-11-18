package com.milenio.govti.controller;
import com.milenio.govti.model.Risk;
import com.milenio.govti.repository.RiskRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/riesgos")
public class RiskController {
    private final RiskRepository repo;
    public RiskController(RiskRepository repo){this.repo=repo;}
    @GetMapping public List<Risk> all(){return repo.findAll();}
    @GetMapping("/{id}") public Risk one(@PathVariable Long id){return repo.findById(id).orElseThrow();}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Risk create(@Valid @RequestBody Risk r){return repo.save(r);}
    @PutMapping("/{id}") public Risk update(@PathVariable Long id,@Valid @RequestBody Risk r){
        Risk old=repo.findById(id).orElseThrow();old.setNombre(r.getNombre());old.setDescripcion(r.getDescripcion());
        old.setCategoria(r.getCategoria());old.setProbabilidad(r.getProbabilidad());old.setImpacto(r.getImpacto());
        old.setNivel(r.getNivel());old.setTratamiento(r.getTratamiento());old.setResponsable(r.getResponsable());
        old.setEstado(r.getEstado());return repo.save(old);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.deleteById(id);}
}
