package com.reagan.shopIt.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.dto.countryDTO.AddCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.DeleteCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.UpdateCountryDTO;
import com.reagan.shopIt.model.exception.CountryDuplicateEntityException;
import com.reagan.shopIt.model.exception.CountryNotFoundException;
import com.reagan.shopIt.repository.CountryRepository;
import com.reagan.shopIt.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.reagan.shopIt.util.ShopItUtil.readResourceFile;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }


    @Override
    @TransactionalEventListener(ApplicationReadyEvent.class)
    public void seedCountry() throws JsonProcessingException {
//        String countriesAsString = readResourceFile("json/countries.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Country[] countriesArray = objectMapper.readValue(countriesAsString, Country[].class);
//        List<Country> countryList = Arrays.asList(countriesArray);
//
//        try {
//            for (Country country : countryList) {
//                if (false) {
//                    Optional<Country> countryExists = countryRepository.findByAbbreviationAndTitle(
//                            country.getAbbreviation(), country.getTitle());
//
//                    if (countryExists.isPresent()) {
//                        continue;
//                    }
//
//                    country.setId(null);
//                    countryRepository.save(country);
//                }
//            }
//        }catch (CountryDuplicateEntityException e){
//
//        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> addNewCountry(AddCountryDTO addCountryDTO) {

        Country country = countryRepository.findByTitle(addCountryDTO.getTitle());
        if (country != null) {
            throw new CountryDuplicateEntityException();
        }
        //Proceed to create country if not exists
        Country country1 = new Country();
        country1.setTitle(addCountryDTO.getTitle());
        country1.setAbbreviation(addCountryDTO.getAbbreviation());

        countryRepository.save(country1);
        return ResponseEntity.status(HttpStatus.CREATED).body("Country added");
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateCountry(UpdateCountryDTO updateCountryDTO) {

        Country country = countryRepository.findByTitle(updateCountryDTO.getOldTitle());
        if (country == null) {
            throw new CountryNotFoundException(updateCountryDTO.getOldTitle());
        }
        // modify country if exists
        country.setTitle(updateCountryDTO.getNewTitle());
        country.setAbbreviation(updateCountryDTO.getAbbreviation());
        country.setUpdatedOn(new Date());
        countryRepository.save(country);

        return ResponseEntity.status(HttpStatus.OK).body("Country Updated");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCountry(DeleteCountryDTO deleteCountryDTO) {
        Country country = countryRepository.findByTitle(deleteCountryDTO.getTitle());
        if (country == null) {
            throw new CountryNotFoundException(deleteCountryDTO.getTitle());
        }
        countryRepository.delete(country);
        return  ResponseEntity.ok("Country deleted");
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.getAllCountries();
    }
}
