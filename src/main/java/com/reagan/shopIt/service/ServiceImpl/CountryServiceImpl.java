package com.reagan.shopIt.service.ServiceImpl;

import com.reagan.shopIt.model.domain.Country;
import com.reagan.shopIt.model.dto.countryDTO.AddCountryDTO;
import com.reagan.shopIt.model.dto.countryDTO.UpdateCountryDTO;
import com.reagan.shopIt.model.exception.CountryDuplicateEntityException;
import com.reagan.shopIt.model.exception.CountryIDNotFoundException;
import com.reagan.shopIt.model.exception.CountryNotFoundException;
import com.reagan.shopIt.repository.CountryRepository;
import com.reagan.shopIt.repository.UserRepository;
import com.reagan.shopIt.service.CountryService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    private final UserRepository userRepository;

    public CountryServiceImpl(CountryRepository countryRepository, UserRepository userRepository) {
        this.countryRepository = countryRepository;
        this.userRepository = userRepository;
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
    @Modifying
    public ResponseEntity<?> updateCountry(UpdateCountryDTO updateCountryDTO) {

        Country country = countryRepository.findByTitle(updateCountryDTO.getOldTitle());
        if (country == null) {
            throw new CountryNotFoundException(updateCountryDTO.getOldTitle());
        }
        // modify country if exists
        country.setTitle(updateCountryDTO.getNewTitle());
        country.setAbbreviation(updateCountryDTO.getAbbreviation());
        countryRepository.save(country);

        return ResponseEntity.status(HttpStatus.OK).body("Country Updated");
    }

    @Override
    @Transactional
    public ResponseEntity<?> deleteCountry(Long id) {
        Country country = countryRepository.findById(id).orElseThrow(
                () -> new CountryIDNotFoundException(id)
        );
        countryRepository.delete(country);
        return  ResponseEntity.ok("Country deleted");
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.getAllCountries();
    }

    @Override
    public List<Object[]> getAllUsersFromCountry(Long countryId) {
        boolean countryExists = countryRepository.existsById(countryId);
        if (!countryExists) {
            throw new CountryIDNotFoundException(countryId);
        }

        List<Object[]> listOfUsers = userRepository.findUserByCountryId(countryId);

        if (listOfUsers.isEmpty()) {
            throw new IllegalArgumentException("we don't have a user from this country yet");
        }
        return listOfUsers;
    }
}
