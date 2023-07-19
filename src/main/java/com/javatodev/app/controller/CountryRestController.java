package com.javatodev.app.controller;

import com.javatodev.app.entity.CountryEntity;
import com.javatodev.app.service.CountryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/country")
@RequiredArgsConstructor
public class CountryRestController {

    private final CountryService countryService;

    @GetMapping
    public ResponseEntity<List<CountryEntity>> readCountries () {
        return ResponseEntity.ok(countryService.findAll());
    }

    @GetMapping(value = "/search")
    public ResponseEntity<List<CountryEntity>> searchCountries (@RequestParam List<Long> countryIds) {
        List<CountryEntity> filteredCountries = countryService.findAll().stream()
            .filter(countryEntity -> countryIds.contains(countryEntity.getId())).collect(Collectors.toList());
        return ResponseEntity.ok(filteredCountries);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CountryEntity> readCountry (@PathVariable("id") Long id) {
        return ResponseEntity.ok(countryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CountryEntity> createCountry (@RequestBody CountryEntity countryEntity) {
        CountryEntity createdCountry = countryService.save(countryEntity);
        return ResponseEntity.ok(createdCountry);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteCountry (@PathVariable("id") Long id) {
        countryService.delete(id);
        return ResponseEntity.status(HttpStatus.PROCESSING).build();
    }

}
