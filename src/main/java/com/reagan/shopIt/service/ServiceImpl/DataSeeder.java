//package com.reagan.shopIt.service.ServiceImpl;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.reagan.shopIt.model.domain.Country;
//import com.reagan.shopIt.model.domain.UserRole;
//import com.reagan.shopIt.repository.CountryRepository;
//import com.reagan.shopIt.repository.RoleRepository;
//import com.reagan.shopIt.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static com.reagan.shopIt.util.ShopItUtil.readResourceFile;
//
//@Component
//public class DataSeeder implements ApplicationRunner {
//
//    private final CountryRepository repository;
//
//    @Autowired
//    public DataSeeder(CountryRepository repository) {
//        this.repository = repository;
//    }
//
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        String userRolesAsString = readResourceFile("json/countries.json");
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        Country[] rolesArray = objectMapper.readValue(userRolesAsString, Country[].class);
//        List<Country> roleList1 = Arrays.asList(rolesArray);
//        repository.saveAll(roleList1);
//
//    }
//}
