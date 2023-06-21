package com.reagan.shopIt.model.dto.roledto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;

public class UserRoleDTO {

    @JsonProperty("title")
    @NotBlank(message = "{role.title.notBlank}")
    private String title;

    @JsonProperty("code")
    @NotBlank(message = "{role.code.notBlank}")
    private String code;
}
