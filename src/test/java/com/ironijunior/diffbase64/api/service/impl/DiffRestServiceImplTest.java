package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.dto.enumerator.DiffStatus;
import com.ironijunior.diffbase64.api.event.DiffEventPublisher;
import com.ironijunior.diffbase64.api.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffRestService;
import com.ironijunior.diffbase64.domain.DifferedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffRestServiceImplTest {

    private DiffRepository repository = Mockito.mock(DiffRepository.class);
    private DiffEventPublisher publisher = Mockito.mock(DiffEventPublisher.class);
    private DiffRestService service;

    @BeforeEach
    public void setup() {
        service = new DiffRestServiceImpl(repository, publisher);
    }

    @Test
    public void convertByteArrayToStringTest() {
        String str = "This is a string transformed in a byte array.";
        byte[] arr = Base64.getEncoder().encode(str.getBytes());

        assertEquals(str, service.convertByteArrayToString(arr));
    }

    @Test
    public void convertNullByteArrayToStringTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.convertByteArrayToString(null));
    }

    @Test
    public void convertEmptyByteArrayToStringTest() {
        Assertions.assertTrue(service.convertByteArrayToString(new byte[0]).isEmpty());
    }

    @Test
    public void savingAnEmptyStringOnLeftTest() {
        DifferedData data = new DifferedData("id");
        Mockito.when(repository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveLeft("id", ""));
    }

    @Test
    public void savingAnEmptyStringOnRightTest() {
        DifferedData data = new DifferedData("id");
        Mockito.when(repository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveRight("id", ""));
    }

    @Test
    public void savingCompleteTest() {
        DifferedData data = new DifferedData("id");
        Mockito.when(repository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(Mockito.any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveLeft("id", "value"));
    }

    @Test
    public void savingAnAlreadyFilledSide() {
        DifferedData data = DifferedData.builder()
            .id("id")
            .left("data")
            .status(DiffStatus.NO_DIFF.getText())
            .build();

        Mockito.when(repository.findById(Mockito.anyString()))
                .thenReturn(Optional.of(data));

        Assertions.assertThrows(SideAlreadyFilledException.class,
                () -> service.saveLeft("id", "data"));
    }
}
