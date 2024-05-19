package com.salestaxapi.service;

import com.salestaxapi.model.TaxRate;
import com.salestaxapi.repository.TaxRateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaxRateService {

    @Autowired
    private TaxRateRepository taxRateRepository;

    public List<TaxRate> getAllTaxRates() {
        return taxRateRepository.findAll();
    }

    public TaxRate addTaxRate(TaxRate taxRate) {
        return taxRateRepository.save(taxRate);
    }

    public TaxRate updateTaxRate(Long id, TaxRate taxRate) {
        TaxRate existingTaxRate = taxRateRepository.findById(id).orElseThrow(() -> new RuntimeException("Tax rate not found"));
        existingTaxRate.setState(taxRate.getState());
        existingTaxRate.setCity(taxRate.getCity());
        existingTaxRate.setTown(taxRate.getTown());
        existingTaxRate.setRate(taxRate.getRate());
        return taxRateRepository.save(existingTaxRate);
    }

    public void deleteTaxRate(Long id) {
        taxRateRepository.deleteById(id);
    }

    public List<TaxRate> findTaxRates(String location) {
        List<TaxRate> taxRates = taxRateRepository.findByState(location);
        if (taxRates.isEmpty()) {
            taxRates = taxRateRepository.findByCity(location);
        }
        if (taxRates.isEmpty()) {
            taxRates = taxRateRepository.findByTown(location);
        }
        return taxRates;
    }
}