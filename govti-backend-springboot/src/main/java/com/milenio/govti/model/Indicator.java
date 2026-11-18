package com.milenio.govti.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name="indicadores")
public class Indicator {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String nombre;
    @Column(length=1000) private String formula;
    private String unidad = "%";
    private Double meta;
    private String frecuencia; // MENSUAL, TRIMESTRAL, SEMESTRAL
    private String responsable;
    @ManyToOne(optional=false) @JoinColumn(name="objetivo_id") private Objective objetivo;

    public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getFormula(){return formula;} public void setFormula(String v){formula=v;}
    public String getUnidad(){return unidad;} public void setUnidad(String v){unidad=v;}
    public Double getMeta(){return meta;} public void setMeta(Double v){meta=v;}
    public String getFrecuencia(){return frecuencia;} public void setFrecuencia(String v){frecuencia=v;}
    public String getResponsable(){return responsable;} public void setResponsable(String v){responsable=v;}
    public Objective getObjetivo(){return objetivo;} public void setObjetivo(Objective v){objetivo=v;}
}
