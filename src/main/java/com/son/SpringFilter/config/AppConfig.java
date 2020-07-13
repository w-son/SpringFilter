package com.son.SpringFilter.config;

import com.son.SpringFilter.domain.Account;
import com.son.SpringFilter.domain.Faker;
import com.son.SpringFilter.domain.UserRole;
import com.son.SpringFilter.service.AccountService;
import com.son.SpringFilter.service.FakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {

        return new ApplicationRunner() {

            @Autowired AccountService accountService;
            @Autowired FakerService fakerService;

            @Override
            public void run(ApplicationArguments args) throws Exception {

                Account account = Account.builder()
                        .username("williamson")
                        .password("1234")
                        .role(UserRole.USER)
                        .build();

                accountService.save(account);

                Faker one = Faker.builder().champion("페블랑").info("페블랑 펜타킬").build();
                Faker two = Faker.builder().champion("페리아나").info("페리아나 5인궁").build();
                Faker three = Faker.builder().champion("페코").info("페코 어그로 핑퐁").build();

                fakerService.save(one);
                fakerService.save(two);
                fakerService.save(three);

            }
        };

    }

}
