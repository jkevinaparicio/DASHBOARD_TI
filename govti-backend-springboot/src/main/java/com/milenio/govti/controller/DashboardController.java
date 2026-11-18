package com.milenio.govti.controller;

import com.milenio.govti.repository.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/dashboard")
public class DashboardController {
    private final ObjectiveRepository objectives; private final IndicatorRepository indicators;
    private final MeasurementRepository measurements; private final RiskRepository risks;
    public DashboardController(ObjectiveRepository o,IndicatorRepository i,MeasurementRepository m,RiskRepository r){
        objectives=o;indicators=i;measurements=m;risks=r;
    }
    @GetMapping public Map<String,Object> summary(){
        Map<String,Object> data=new LinkedHashMap<>();
        data.put("totalObjetivos",objectives.count());data.put("totalIndicadores",indicators.count());
        data.put("totalMediciones",measurements.count());data.put("totalRiesgos",risks.count());
        data.put("mensaje","Resumen calculado con los registros existentes en GOVTI");
        return data;
    }
}
