package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffRestService;
import com.ironijunior.diffbase64.domain.DifferedData;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;

@Service
public class DiffRestServiceImpl implements DiffRestService {

    private DiffRepository diffRepository;

    @Autowired
    public DiffRestServiceImpl(DiffRepository repository) {
        this.diffRepository = repository;
    }

    @Override
    public String convertByteArrayToString(byte[] arr) {
        if (Objects.isNull(arr)) {
            throw new IllegalArgumentException();
        }
        return new String(Base64.decodeBase64(arr), Charset.defaultCharset());
    }

    @Override
    public DifferedData getById(String id) {
        return null;
    }

    @Override
    public boolean saveLeft(String id, String value) {
        return true;
    }

    @Override
    public boolean saveRight(String id, String value) {
        return true;
    }

}
