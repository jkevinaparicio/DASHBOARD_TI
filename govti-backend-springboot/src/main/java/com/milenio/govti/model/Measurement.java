package com.milenio.govti.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity @Table(name="mediciones")
public class Measurement {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(optional=false) @JoinColumn(name="indicador_id") private Indicator indicador;
    @NotNull private Double valor;
    @NotNull private LocalDate fecha;
    @Column(length=1000) private String observacion;
    private String registradoPor;

    public Long getId(){return id;} public Indicator getIndicador(){return indicador;} public void setIndicador(Indicator v){indicador=v;}
    public Double getValor(){return valor;} public void setValor(Double v){valor=v;}
    public LocalDate getFecha(){return fecha;} public void setFecha(LocalDate v){fecha=v;}
    public String getObservacion(){return observacion;} public void setObservacion(String v){observacion=v;}
    public String getRegistradoPor(){return registradoPor;} public void setRegistradoPor(String v){registradoPor=v;}
}
