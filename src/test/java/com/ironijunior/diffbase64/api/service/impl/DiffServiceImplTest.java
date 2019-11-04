package com.ironijunior.diffbase64.api.service.impl;

import com.ironijunior.diffbase64.api.repository.DiffRepository;
import com.ironijunior.diffbase64.api.service.DiffService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DiffServiceImplTest {

    private DiffRepository repository = Mockito.mock(DiffRepository.class);
    private DiffService service;

    @BeforeEach
    public void setup() {
        service = new DiffServiceImpl(repository);
    }

    @Test
    public void convertByteArrayToStringTest() {
        String expected = "This is a string transformed in a byte array.";
        assertEquals(expected, service.convertByteArrayToString(expected.getBytes()));
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

}
