package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.domain.DifferedData;
import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiffServiceImpl implements DiffService {

    private DiffRepository repository;

    @Autowired
    public DiffServiceImpl(DiffRepository repository) {
        this.repository = repository;
    }

    public boolean save(DifferedData data) {
        return true;
    }
}
