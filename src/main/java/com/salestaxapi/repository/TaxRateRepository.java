package com.salestaxapi.repository;


import com.salestaxapi.model.TaxRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaxRateRepository extends JpaRepository<TaxRate, Long> {
    List<TaxRate> findByState(String state);
    List<TaxRate> findByCity(String city);
    List<TaxRate> findByTown(String town);
}
