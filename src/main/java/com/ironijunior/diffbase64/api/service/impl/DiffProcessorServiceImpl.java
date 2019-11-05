package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiffProcessorServiceImpl implements DiffProcessorService {

    private DiffRepository repository;

    @Autowired
    public DiffProcessorServiceImpl(DiffRepository repository) {
        this.repository = repository;
    }
}
