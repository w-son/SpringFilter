package com.son.SpringFilter.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id @GeneratedValue
    private Long id;

    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserRole role;

}
