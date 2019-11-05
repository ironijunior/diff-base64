package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.domain.enumerator.DiffStatus;
import com.ironijunior.diffbase64.api.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.api.exception.SideAlreadyFilledException;
import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffRestService;
import com.ironijunior.diffbase64.domain.entity.DifferedData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Base64;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;

public class DiffRestServiceImplTest {

    public static final String VALID_ID = "id";
    public static final String INVALID_ID = "abc";
    public static final String DATA = "data";
    private DiffRepository repository = Mockito.mock(DiffRepository.class);
    private DiffRestService service;


    @BeforeEach
    public void setup() {
        service = new DiffRestServiceImpl(repository);
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
        DifferedData data = new DifferedData(VALID_ID);
        Mockito.when(repository.findById(anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveLeft(VALID_ID, ""));
    }

    @Test
    public void savingAnEmptyStringOnRightTest() {
        DifferedData data = new DifferedData(VALID_ID);
        Mockito.when(repository.findById(anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveRight(VALID_ID, ""));
    }

    @Test
    public void savingCompleteTest() {
        DifferedData data = new DifferedData(VALID_ID);
        Mockito.when(repository.findById(anyString()))
                .thenReturn(Optional.of(data));
        Mockito.when(repository.save(any()))
                .thenReturn(data);

        Assertions.assertTrue(service.saveLeft(VALID_ID, DATA));
    }

    @Test
    public void savingAnAlreadyFilledSide() {
        DifferedData data = DifferedData.builder()
            .id(VALID_ID)
            .left(DATA)
            .status(DiffStatus.NO_DIFF.getText())
            .build();

        Mockito.when(repository.findById(anyString()))
                .thenReturn(Optional.of(data));

        Assertions.assertThrows(SideAlreadyFilledException.class,
                () -> service.saveLeft(VALID_ID, DATA));
    }

    @Test
    public void savingAnInexistentIdTest() {
        Mockito.when(repository.findById(eq(INVALID_ID)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> service.saveLeft(INVALID_ID, DATA));
    }

    @Test
    public void savingANullIdTest() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.saveLeft(null, DATA));
    }

    @Test
    public void gettingDifferedDataFromValidIdTest() {
        DifferedData data = DifferedData.builder()
                .id(VALID_ID)
                .left(DATA)
                .status(DiffStatus.NO_DIFF.getText())
                .build();

        Mockito.when(repository.findById(anyString()))
                .thenReturn(Optional.of(data));

        Assertions.assertEquals(data, service.getById(VALID_ID));
    }

    @Test
    public void gettingDifferedDataFromNullIdTest() {
        Mockito.when(repository.findById(isNull()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> service.getById(null));
    }

    @Test
    public void gettingDifferedDataFromInvalidIdTest() {
        Mockito.when(repository.findById(eq(INVALID_ID)))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> service.getById(INVALID_ID));
    }
}
