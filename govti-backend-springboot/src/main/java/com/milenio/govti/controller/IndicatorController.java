package com.milenio.govti.controller;
import com.milenio.govti.model.*;
import com.milenio.govti.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/indicadores")
public class IndicatorController {
    private final IndicatorRepository repo; private final ObjectiveRepository objectives;
    public IndicatorController(IndicatorRepository repo,ObjectiveRepository objectives){this.repo=repo;this.objectives=objectives;}
    @GetMapping public List<Indicator> all(){return repo.findAll();}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Indicator create(@Valid @RequestBody Indicator i){
        Long id=i.getObjetivo().getId(); i.setObjetivo(objectives.findById(id).orElseThrow());return repo.save(i);
    }
    @PutMapping("/{id}") public Indicator update(@PathVariable Long id,@Valid @RequestBody Indicator i){
        Indicator old=repo.findById(id).orElseThrow();old.setNombre(i.getNombre());old.setFormula(i.getFormula());
        old.setUnidad(i.getUnidad());old.setMeta(i.getMeta());old.setFrecuencia(i.getFrecuencia());old.setResponsable(i.getResponsable());
        old.setObjetivo(objectives.findById(i.getObjetivo().getId()).orElseThrow());return repo.save(old);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.deleteById(id);}
}
