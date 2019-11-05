package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.event.DiffEvent;
import com.ironijunior.diffbase64.api.event.DiffEventPublisher;
import com.ironijunior.diffbase64.domain.enumerator.DiffSide;
import com.ironijunior.diffbase64.api.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.api.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffRestService;
import com.ironijunior.diffbase64.domain.entity.DifferedData;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiffRestServiceImpl implements DiffRestService {

    private DiffRepository diffRepository;
    private DiffEventPublisher diffEventPublisher;

    @Autowired
    public DiffRestServiceImpl(DiffRepository repository, DiffEventPublisher publisher) {
        this.diffRepository = repository;
        this.diffEventPublisher = publisher;
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
        return getById(id, false);
    }

    @Override
    public boolean saveLeft(String id, String value) {
        return save(id, value, DiffSide.LEFT);
    }

    @Override
    public boolean saveRight(String id, String value) {
        return save(id, value, DiffSide.RIGHT);
    }

    private boolean save(String id, String value, DiffSide side) {
        DifferedData diffById = getById(id, true);
        if (isSideAlreadyFilled(diffById, side)) {
            throw new SideAlreadyFilledException(side);
        }

        if (DiffSide.RIGHT.equals(side)) {
            diffById.setRight(value);
        } else {
            diffById.setLeft(value);
        }
        DifferedData savedData = diffRepository.save(diffById);
        if (isComplete(savedData)) {
            sendToDiffer(savedData);
        }
        return true;
    }

    private boolean isSideAlreadyFilled(DifferedData diffById, DiffSide side) {
        return (DiffSide.RIGHT.equals(side) && Objects.nonNull(diffById.getRight()))
                || (DiffSide.LEFT.equals(side) && Objects.nonNull(diffById.getLeft()));
    }

    private DifferedData getById(String id, boolean returnNewIfEmpty) {
        if (Objects.isNull(id)) {
            throw new IllegalArgumentException("ID should not be null.");
        }

        Optional<DifferedData> optData = diffRepository.findById(id);
        if (returnNewIfEmpty) {
            return optData.orElse(new DifferedData(id));
        }
        return optData.orElseThrow(() -> new EntityNotFoundException(id));
    }

    private boolean isComplete(DifferedData data) {
        return Objects.nonNull(data.getLeft())
                && Objects.nonNull(data.getRight());
    }

    private void sendToDiffer(DifferedData data) {
        diffEventPublisher.publish(new DiffEvent(this, data));
    }
}
