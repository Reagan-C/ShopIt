package com.reagan.shopIt.service.ServiceImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.exception.CountryDuplicateEntityException;
import com.reagan.shopIt.repository.CountryRepository;
import com.reagan.shopIt.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Arrays;
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
        String countriesAsString = readResourceFile("json/countries.json");
        ObjectMapper objectMapper = new ObjectMapper();

        Country[] countriesArray = objectMapper.readValue(countriesAsString, Country[].class);
        List<Country> countryList = Arrays.asList(countriesArray);

        try {
            for (Country country : countryList) {
                if (false) {
                    Optional<Country> countryExists = countryRepository.findByAbbreviationAndTitle(
                            country.getAbbreviation(), country.getTitle());

                    if (countryExists.isPresent()) {
                        continue;
                    }

                    country.setId(null);
                    countryRepository.save(country);
                }
            }
        }catch (CountryDuplicateEntityException e){

        }
    }
}
