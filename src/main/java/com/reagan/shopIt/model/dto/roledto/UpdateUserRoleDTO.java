package com.reagan.shopIt.model.dto.roledto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdateUserRoleDTO {
    @JsonProperty("title")
    @NotBlank(message = "{role.title.notBlank}")
    private String newTitle;

    @JsonProperty("code")
    @NotBlank(message = "{role.code.notBlank}")
    private String code;
}
