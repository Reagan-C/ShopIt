package com.reagan.shopIt.model.dto.admindto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.reagan.shopIt.model.domain.User;
import jakarta.validation.constraints.NotBlank;

public class AdminDTO {

    @JsonProperty("user")
    @NotBlank(message = "{admin.user.notBlank}")
    private User user;
}
