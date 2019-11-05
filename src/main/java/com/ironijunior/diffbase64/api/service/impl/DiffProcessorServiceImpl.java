package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffProcessorService;
import com.ironijunior.diffbase64.domain.entity.DifferedData;
import com.ironijunior.diffbase64.domain.entity.DifferenceData;
import com.ironijunior.diffbase64.domain.enumerator.DiffStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiffProcessorServiceImpl implements DiffProcessorService {

    private DiffRepository diffRepository;

    @Autowired
    public DiffProcessorServiceImpl(DiffRepository repository) {
        this.diffRepository = repository;
    }

    private static DifferenceData getNewDifference(long initialOffset, long length) {
        return new DifferenceData(initialOffset, initialOffset + length, length);
    }

    @Override
    public DifferedData diffSides(DifferedData data) {
        DifferedData.DifferedDataBuilder diffBuilder = DifferedData.builder()
                .id(data.getId())
                .left(data.getLeft())
                .right(data.getRight());

        DiffStatus status = getDiffStatus(data);
        diffBuilder.status(status.getText());

        if (DiffStatus.DIFFERENT.equals(status)) {
            diffBuilder.differences(getDifferencesList(data.getLeft(), data.getRight()));
        }
        return diffRepository.save(diffBuilder.build());
    }

    private DiffStatus getDiffStatus(DifferedData data) {
        if (data.getLeft().equals(data.getRight())) {
            return DiffStatus.EQUALS;
        } else if (data.getLeft().length() != data.getRight().length()) {
            return DiffStatus.DIFFERENT_SIZES;
        }
        return DiffStatus.DIFFERENT;
    }

    private List<DifferenceData> getDifferencesList(String left, String right) {
        List<DifferenceData> differences = new ArrayList<>();

        long initialOffset = 0;
        long length = 0;
        boolean isDifferent = false;

        for (int i = 0; i < left.length(); i++) {
            if (left.charAt(i) != right.charAt(i)) {
                if (!isDifferent) {
                    initialOffset = i;
                }
                length += 1;
                isDifferent = true;
            } else {
                if (isDifferent) {
                    differences.add(getNewDifference(initialOffset, length));
                    isDifferent = false;
                    length = 0;
                }
            }
        }
        if (isDifferent) {
            differences.add(getNewDifference(initialOffset, length));
        }
        return differences;
    }
}
