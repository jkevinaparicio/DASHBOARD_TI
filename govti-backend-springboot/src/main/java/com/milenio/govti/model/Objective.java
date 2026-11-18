package com.milenio.govti.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity @Table(name="objetivos")
public class Objective {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String nombre;
    @Column(length=1000) private String descripcion;
    @NotBlank private String perspectiva; // FINANCIERA, CLIENTES, PROCESOS, APRENDIZAJE
    private String responsable;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado = "PENDIENTE";

    public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
    public String getPerspectiva(){return perspectiva;} public void setPerspectiva(String v){perspectiva=v;}
    public String getResponsable(){return responsable;} public void setResponsable(String v){responsable=v;}
    public LocalDate getFechaInicio(){return fechaInicio;} public void setFechaInicio(LocalDate v){fechaInicio=v;}
    public LocalDate getFechaFin(){return fechaFin;} public void setFechaFin(LocalDate v){fechaFin=v;}
    public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
}
