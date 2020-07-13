package com.son.SpringFilter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    @JsonProperty(value = "Username")
    private String username;

    @JsonProperty(value = "JWT")
    private String accessToken;

}
