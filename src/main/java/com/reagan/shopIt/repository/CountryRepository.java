package com.reagan.shopIt.repository;

import com.reagan.shopIt.model.domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    @Query(value = "select i from Country i", nativeQuery = true)
    List<Country> getAllCountries();
}
