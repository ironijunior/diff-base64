package com.ironijunior.diffbase64.api.controller;

import com.ironijunior.diffbase64.api.service.DiffRestService;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

public class DiffControllerTest {

    private DiffRestService service = Mockito.mock(DiffRestService.class);
    private DiffController controller;

    @BeforeEach
    public void setup() {
        controller = new DiffController(service);
    }

}
