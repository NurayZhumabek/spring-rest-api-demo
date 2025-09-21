package com.example.FirstRestApp.repositories;

import com.example.FirstRestApp.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MeasurementsRepository extends JpaRepository<Measurement,Integer> {


     int countByRaining(boolean raining);

     @Query("SELECT m.value FROM Measurement m")
     List<Integer> getMeasurementByValue();
}
