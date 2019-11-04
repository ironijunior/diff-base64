package com.ironijunior.diffbase64.api.controller;

import com.ironijunior.diffbase64.api.service.DiffService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DiffControllerTest {

    private DiffService service = Mockito.mock(DiffService.class);
    private DiffController controller;

    @BeforeEach
    public void setup() {
        controller = new DiffController(service);
    }

}
