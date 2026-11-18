package com.milenio.govti.controller;
import com.milenio.govti.model.*;
import com.milenio.govti.repository.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/mediciones")
public class MeasurementController {
    private final MeasurementRepository repo; private final IndicatorRepository indicators;
    public MeasurementController(MeasurementRepository repo,IndicatorRepository indicators){this.repo=repo;this.indicators=indicators;}
    @GetMapping public List<Measurement> all(){return repo.findAll();}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Measurement create(@Valid @RequestBody Measurement m){
        m.setIndicador(indicators.findById(m.getIndicador().getId()).orElseThrow());return repo.save(m);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id){repo.deleteById(id);}
}
