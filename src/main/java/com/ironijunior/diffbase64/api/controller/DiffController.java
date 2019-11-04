package com.ironijunior.diffbase64.api.controller;

import com.ironijunior.diffbase64.api.dto.DiffRequestDTO;
import com.ironijunior.diffbase64.api.dto.DiffResponseDTO;
import com.ironijunior.diffbase64.api.service.DiffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/diff")
public class DiffController {

    private DiffService service;

    @Autowired
    public DiffController(DiffService service) {
        this.service = service;
    }

    @PostMapping("/{id}/left")
    public ResponseEntity<Void> saveLeft(@PathVariable String id, @RequestBody DiffRequestDTO request) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/right")
    public ResponseEntity<Void> saveRight(@PathVariable String id, @RequestBody DiffRequestDTO request) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiffResponseDTO> getDiff(@PathVariable String id) {
        return ResponseEntity.ok(new DiffResponseDTO());
    }
}
