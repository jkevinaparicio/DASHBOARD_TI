package com.milenio.govti.controller;
import com.milenio.govti.model.Objective;
import com.milenio.govti.repository.ObjectiveRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/objetivos")
public class ObjectiveController {
    private final ObjectiveRepository repo;
    public ObjectiveController(ObjectiveRepository repo){this.repo=repo;}
    @GetMapping public List<Objective> all(){return repo.findAll();}
    @GetMapping("/{id}") public Objective one(@PathVariable Long id){return repo.findById(id).orElseThrow();}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Objective create(@Valid @RequestBody Objective o){return repo.save(o);}
    @PutMapping("/{id}") public Objective update(@PathVariable Long id,@Valid @RequestBody Objective o){
        Objective old=repo.findById(id).orElseThrow(); old.setNombre(o.getNombre());old.setDescripcion(o.getDescripcion());
        old.setPerspectiva(o.getPerspectiva());old.setResponsable(o.getResponsable());old.setFechaInicio(o.getFechaInicio());
        old.setFechaFin(o.getFechaFin());old.setEstado(o.getEstado());return repo.save(old);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.deleteById(id);}
}
