package com.milenio.govti.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity @Table(name="riesgos")
public class Risk {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank private String nombre;
    @Column(length=1200) private String descripcion;
    @NotBlank private String categoria;
    @NotBlank private String probabilidad; // BAJA, MEDIA, ALTA
    @NotBlank private String impacto; // BAJO, MEDIO, ALTO
    private String nivel; // calculado por el responsable
    @Column(length=1200) private String tratamiento;
    private String responsable;
    private String estado = "ABIERTO";

    public Long getId(){return id;} public String getNombre(){return nombre;} public void setNombre(String v){nombre=v;}
    public String getDescripcion(){return descripcion;} public void setDescripcion(String v){descripcion=v;}
    public String getCategoria(){return categoria;} public void setCategoria(String v){categoria=v;}
    public String getProbabilidad(){return probabilidad;} public void setProbabilidad(String v){probabilidad=v;}
    public String getImpacto(){return impacto;} public void setImpacto(String v){impacto=v;}
    public String getNivel(){return nivel;} public void setNivel(String v){nivel=v;}
    public String getTratamiento(){return tratamiento;} public void setTratamiento(String v){tratamiento=v;}
    public String getResponsable(){return responsable;} public void setResponsable(String v){responsable=v;}
    public String getEstado(){return estado;} public void setEstado(String v){estado=v;}
}
