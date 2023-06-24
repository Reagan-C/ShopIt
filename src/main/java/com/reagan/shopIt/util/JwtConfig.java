package com.reagan.shopIt.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
public class JwtConfig {

    @Value("${jwt.secret}")
    private String secret;
    private long expiration;
    private String prefix;
    private String header;

}

