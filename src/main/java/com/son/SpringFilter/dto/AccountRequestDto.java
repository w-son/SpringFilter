package com.son.SpringFilter.dto;

import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {

    private String username;

    private String password;

}
