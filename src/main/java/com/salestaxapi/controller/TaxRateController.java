package com.salestaxapi.controller;

import com.salestaxapi.model.TaxRate;
import com.salestaxapi.service.TaxRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxRates")
public class TaxRateController {

    @Autowired
    private TaxRateService taxRateService;

    @GetMapping
    public List<TaxRate> getAllTaxRates() {
        return taxRateService.getAllTaxRates();
    }

    @PostMapping
    public ResponseEntity<TaxRate> addTaxRate(@RequestBody TaxRate taxRate) {
        TaxRate newTaxRate = taxRateService.addTaxRate(taxRate);
        return new ResponseEntity<>(newTaxRate, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxRate> updateTaxRate(@PathVariable Long id, @RequestBody TaxRate taxRate) {
        TaxRate updatedTaxRate = taxRateService.updateTaxRate(id, taxRate);
        return new ResponseEntity<>(updatedTaxRate, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTaxRate(@PathVariable Long id) {
        taxRateService.deleteTaxRate(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<TaxRate>> findTaxRates(@RequestParam String location) {
        List<TaxRate> taxRates = taxRateService.findTaxRates(location);
        if (taxRates.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(taxRates, HttpStatus.OK);
    }
}