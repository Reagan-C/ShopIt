package com.reagan.shopIt.controllers.country;

import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.dto.countryDTO.AddCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.DeleteCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.UpdateCountryDTO;
import com.reagan.shopIt.service.CountryService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "country", produces = {MediaType.APPLICATION_JSON_VALUE},
                consumes = {MediaType.APPLICATION_JSON_VALUE})
@PreAuthorize("hasRole('Administrator')")
public class CountryController {

    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCountry(@Validated @RequestBody AddCountryDTO addCountryDTO) {
        return countryService.addNewCountry(addCountryDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCountryDetails(@Validated @RequestBody UpdateCountryDTO countryDTO) {
        return countryService.updateCountry(countryDTO);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> removeCountry(@RequestParam("id") Long id) {
        return countryService.deleteCountry(id);
    }

    @GetMapping("/get-all-countries")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/get-all-users")
    public List<Object[]> getUsersFromCountry(@RequestParam("country_id") Long id) {
        return countryService.getAllUsersFromCountry(id);
    }

}
