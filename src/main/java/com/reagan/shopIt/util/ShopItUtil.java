package com.reagan.shopIt.util;

import com.reagan.shopIt.config.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static org.hibernate.engine.config.spi.StandardConverters.asString;

@Component
public class ShopItUtil {

    @Autowired
    private JwtTokenProvider tokenProvider;

    public static Pageable createPageable(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        return PageRequest.of(pageNo, pageSize, sort);
    }

    public static String readResourceFile(String path) {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(path);
        return asString(resource);
    }

    public ResponseEntity<String> signInResponse() {
        return null;
    }

    public static UserDetails getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principal = context.getAuthentication().getPrincipal();
        System.out.println(principal);
        if (principal instanceof UserDetails) {
            System.out.println((UserDetails) context.getAuthentication().getPrincipal());
            return (UserDetails) context.getAuthentication().getPrincipal();
        }
        else {
            return null;
        }
    }
}
