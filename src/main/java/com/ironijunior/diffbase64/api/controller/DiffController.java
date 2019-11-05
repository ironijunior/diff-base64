package com.ironijunior.diffbase64.api.controller;

import com.ironijunior.diffbase64.api.dto.DiffResponseDTO;
import com.ironijunior.diffbase64.api.service.DiffRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/diff")
public class DiffController {

    private DiffRestService service;

    @Autowired
    public DiffController(DiffRestService service) {
        this.service = service;
    }

    @PostMapping("/{id}/left")
    public ResponseEntity<Boolean> saveLeft(@PathVariable String id, @RequestBody byte[] request) {
        String data = service.convertByteArrayToString(request);
        return new ResponseEntity<>(service.saveLeft(id, data), HttpStatus.CREATED);
    }

    @PostMapping("/{id}/right")
    public ResponseEntity<Boolean> saveRight(@PathVariable String id, @RequestBody byte[] request) {
        String data = service.convertByteArrayToString(request);
        return new ResponseEntity<>(service.saveRight(id, data), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiffResponseDTO> getDiff(@PathVariable String id) {
        DiffResponseDTO dto = DiffResponseDTO.convertFromEntity(service.getById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
