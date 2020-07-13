package com.son.SpringFilter.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Faker {

    @Id @GeneratedValue
    private Long id;

    private String champion;

    private String info;

}
