package com.ironijunior.diffbase64.transport.controller;

import com.ironijunior.diffbase64.transport.dto.DiffResponseDTO;
import com.ironijunior.diffbase64.domain.enumerator.DiffStatus;
import com.ironijunior.diffbase64.domain.exception.EntityNotFoundException;
import com.ironijunior.diffbase64.service.DiffRestService;
import com.ironijunior.diffbase64.domain.DifferedData;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;

public class DiffControllerTest {

    private DiffRestService service = Mockito.mock(DiffRestService.class);
    private DiffController controller;

    private String id = "ID";
    private String text = "any text to be converted";
    private byte[] arr = Base64.encodeBase64(text.getBytes());

    @BeforeEach
    public void setup() {
        controller = new DiffController(service);
    }

    @Test
    public void callingLeftEndpointWithValidArrayTest() {
        Mockito.when(service.convertByteArrayToString(any()))
                .thenReturn(text);
        Mockito.when(service.saveLeft(anyString(), anyString()))
                .thenReturn(Boolean.TRUE);

        ResponseEntity<Void> response = controller.saveLeft(id, arr);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void callingRightEndpointWithValidArrayTest() {
        Mockito.when(service.convertByteArrayToString(any()))
                .thenReturn(text);
        Mockito.when(service.saveRight(anyString(), anyString()))
                .thenReturn(Boolean.TRUE);

        ResponseEntity<Void> response = controller.saveRight(id, arr);
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertNull(response.getBody());
    }

    @Test
    public void callingLeftEndpointWithNullArrayTest() {
        Mockito.when(service.convertByteArrayToString(isNull()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.saveLeft(id, null));
    }

    @Test
    public void callingRightEndpointWithNullArrayTest() {
        Mockito.when(service.convertByteArrayToString(isNull()))
                .thenThrow(IllegalArgumentException.class);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> controller.saveRight(id, null));
    }

    @Test
    public void callingGetDiffsWithAValidId() {
        DifferedData data = DifferedData.builder()
                .id(id)
                .left(text)
                .right(text + "b")
                .status(DiffStatus.DIFFERENT_SIZES.getText())
                .differences(Collections.emptyList())
                .build();

        DiffResponseDTO dto = DiffResponseDTO.convertFromEntity(data);

        Mockito.when(service.getById(anyString()))
                .thenReturn(data);

        ResponseEntity<DiffResponseDTO> response = controller.getDiff(id);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(dto, (DiffResponseDTO)response.getBody());
    }

    @Test
    public void callingGetDiffsWithANullId() {
        Mockito.when(service.getById(isNull()))
                .thenThrow(EntityNotFoundException.class);

        Assertions.assertThrows(EntityNotFoundException.class,
                () -> controller.getDiff(null));
    }
}
