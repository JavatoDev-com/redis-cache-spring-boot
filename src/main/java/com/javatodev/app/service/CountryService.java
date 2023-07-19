package com.javatodev.app.service;

import com.javatodev.app.entity.CountryEntity;
import com.javatodev.app.repository.CountryRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CountryService {
    private static final String REDIS_CACHE_COUNTRY_VALUE = "country";
    private static final String REDIS_CACHE_COUNTRIES_VALUE = "countries";

    private final CountryRepository countryRepository;

    //    @CachePut(value = REDIS_CACHE_COUNTRY_VALUE, key = "#countryEntity.id")
    @Caching(
        put = {
            @CachePut(value = REDIS_CACHE_COUNTRY_VALUE, key = "#countryEntity.id")
        },
        evict = {
            @CacheEvict(value = REDIS_CACHE_COUNTRIES_VALUE, allEntries = true)
        })
    public CountryEntity save(CountryEntity countryEntity) {

        return countryRepository.save(countryEntity);
    }

    @Cacheable(value = REDIS_CACHE_COUNTRY_VALUE, key = "#id")
    public CountryEntity findById(Long id) {
        return countryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @CacheEvict(value = REDIS_CACHE_COUNTRY_VALUE, key = "#id")
    public void delete(Long id) {
        countryRepository.deleteById(id);
//        return countryRepository.findAll();
    }

    @Cacheable(value = REDIS_CACHE_COUNTRIES_VALUE)
    public List<CountryEntity> findAll() {
        return countryRepository.findAll();
    }
}
