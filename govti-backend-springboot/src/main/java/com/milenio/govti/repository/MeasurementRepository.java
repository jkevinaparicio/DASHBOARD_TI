package com.milenio.govti.repository;
import com.milenio.govti.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MeasurementRepository extends JpaRepository<Measurement,Long> {}
