package com.son.SpringFilter.service;

import com.son.SpringFilter.domain.Faker;
import com.son.SpringFilter.repository.FakerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FakerService {

    private final FakerRepository fakerRepository;

    public Faker save(Faker faker) {
        return fakerRepository.save(faker);
    }

    public List<Faker> findAll() {
        return fakerRepository.findAll();
    }

}
