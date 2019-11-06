package com.ironijunior.diffbase64.service.impl;

import com.ironijunior.diffbase64.service.impl.DiffProcessorServiceImpl;
import com.ironijunior.diffbase64.transport.repository.DiffRepository;
import com.ironijunior.diffbase64.domain.DifferedData;
import com.ironijunior.diffbase64.domain.DifferenceData;
import com.ironijunior.diffbase64.domain.enumerator.DiffStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;

public class DiffProcessorServiceImplTest {

    public static final String ID = "id";
    private DiffProcessorServiceImpl service;
    private DiffRepository repository = Mockito.mock(DiffRepository.class);

    @BeforeEach
    public void setup() {
        this.service = new DiffProcessorServiceImpl(repository);
    }

    @Test
    public void diffBetweenEqualsStrings() {
        String same = "This string is the best";

        DifferedData data = DifferedData.builder().id(ID)
                .left(same).right(same).build();

        DifferedData expectData = DifferedData.builder().id(ID)
                .left(same).right(same).status(DiffStatus.EQUALS.getText()).build();

        Mockito.when(repository.save(any()))
                .thenReturn(expectData);

        service.diffSides(data);

        Mockito.verify(repository).save(argThat((DifferedData dd) -> {
            Assertions.assertEquals(expectData.getId(), dd.getId());
            Assertions.assertEquals(expectData.getLeft(), dd.getLeft());
            Assertions.assertEquals(expectData.getRight(), dd.getRight());
            Assertions.assertEquals(expectData.getStatus(), dd.getStatus());
            Assertions.assertNull(dd.getDifferences());
            return true;
        }));
    }

    @Test
    public void diffBetweenDifferentSizeStrings() {
        String left = "This string is the complete";
        String right = "This string not";

        DifferedData data = DifferedData.builder().id(ID)
                .left(left).right(right).build();

        DifferedData expectData = DifferedData.builder().id(ID)
                .left(left).right(right).status(DiffStatus.DIFFERENT_SIZES.getText()).build();

        Mockito.when(repository.save(any()))
                .thenReturn(expectData);

        service.diffSides(data);

        Mockito.verify(repository).save(argThat((DifferedData dd) -> {
            Assertions.assertEquals(expectData.getId(), dd.getId());
            Assertions.assertEquals(expectData.getLeft(), dd.getLeft());
            Assertions.assertEquals(expectData.getRight(), dd.getRight());
            Assertions.assertEquals(expectData.getStatus(), dd.getStatus());
            Assertions.assertNull(dd.getDifferences());
            return true;
        }));
    }

    @Test
    public void diffBetweenSameSizeButNotEqualStrings() {
        String left  = "This string is the best";
        String right = "This string is not good";

        List<DifferenceData> diffs = Arrays.asList(
                new DifferenceData(15L, 15L + 3L, 3L),
                new DifferenceData(19L, 19L + 4L, 4L)
        );

        DifferedData toBeProcessed = DifferedData.builder().id(ID)
                .left(left).right(right).build();

        DifferedData expectData = DifferedData.builder().id(ID)
                .left(left).right(right).status(DiffStatus.DIFFERENT.getText())
                .differences(diffs)
                .build();

        Mockito.when(repository.save(any())).thenReturn(expectData);

        service.diffSides(toBeProcessed);

        Mockito.verify(repository).save(argThat((DifferedData dd) -> {
            Assertions.assertEquals(expectData.getId(), dd.getId());
            Assertions.assertEquals(expectData.getLeft(), dd.getLeft());
            Assertions.assertEquals(expectData.getRight(), dd.getRight());
            Assertions.assertEquals(expectData.getStatus(), dd.getStatus());
            Assertions.assertEquals(expectData.getDifferences().size(), dd.getDifferences().size());
            return true;
        }));

    }
}
