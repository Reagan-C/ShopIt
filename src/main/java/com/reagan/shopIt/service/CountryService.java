package com.reagan.shopIt.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.dto.countryDTO.AddCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.DeleteCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.UpdateCountryDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CountryService {

    void seedCountry() throws JsonProcessingException;

    @Transactional
    ResponseEntity<?> addNewCountry(AddCountryDTO addCountryDTO);

    @Transactional
    ResponseEntity<?> updateCountry(UpdateCountryDTO updateCountryDTO);

    @Transactional
    ResponseEntity<?> deleteCountry(DeleteCountryDTO deleteCountryDTO);

    List<Country> getAllCountries();
}
