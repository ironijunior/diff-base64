package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.repository.DiffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DifferProcessor {

    private DiffRepository repository;

    @Autowired
    public DifferProcessor(DiffRepository repository) {
        this.repository = repository;
    }
}
