package com.ironijunior.diffbase64.service.impl;

import com.ironijunior.diffbase64.transport.event.DiffEvent;
import com.ironijunior.diffbase64.transport.event.DiffEventPublisher;
import com.ironijunior.diffbase64.domain.enumerator.DiffSide;
import com.ironijunior.diffbase64.domain.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.domain.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.transport.repository.DiffRepository;
import com.ironijunior.diffbase64.service.DiffRestService;
import com.ironijunior.diffbase64.domain.DifferedData;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@Service
public class DiffRestServiceImpl implements DiffRestService {

    private static final Logger logger = LoggerFactory.getLogger(DiffRestServiceImpl.class);

    private DiffRepository diffRepository;
    private DiffEventPublisher diffEventPublisher;

    @Autowired
    public DiffRestServiceImpl(DiffRepository repository, DiffEventPublisher publisher) {
        this.diffRepository = repository;
        this.diffEventPublisher = publisher;
    }

    @Override
    public String convertByteArrayToString(byte[] arr) {
        logger.info("Converting and decoding body");
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
        logger.info("Saving the {} side of the id {}", side, id);

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
        if (isBothSideFilled(savedData)) {
            sendToDifferProcessor(savedData);
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

    private boolean isBothSideFilled(DifferedData data) {
        return Objects.nonNull(data.getLeft())
                && Objects.nonNull(data.getRight());
    }

    private void sendToDifferProcessor(DifferedData data) {
        logger.info("Publishing event to start the diff process");
        diffEventPublisher.publish(new DiffEvent(this, data));
    }
}
