package com.ironijunior.diffbase64.transport.controller;

import com.ironijunior.diffbase64.transport.dto.DiffResponseDTO;
import com.ironijunior.diffbase64.service.DiffRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/***
 * Exposes the application REST api.
 *
 * @author Ironi Junior Medina
 */
@RestController
@RequestMapping("/v1/diff")
public class DiffController {

    private DiffRestService service;

    @Autowired
    public DiffController(DiffRestService service) {
        this.service = service;
    }

    /**
     * Method responsible for saving the left side to be diff-ed.
     *
     * @param id diff identification
     * @param request data to be saved on the left side.
     * @return Response
     */
    @PostMapping("/{id}/left")
    public ResponseEntity<Boolean> saveLeft(@PathVariable String id, @RequestBody byte[] request) {
        String data = service.convertByteArrayToString(request);
        return new ResponseEntity<>(service.saveLeft(id, data), HttpStatus.CREATED);
    }

    /**
     * Method responsible for saving the right side to be diff-ed.
     *
     * @param id diff identification
     * @param request data to be saved on the right side.
     * @return Response
     */
    @PostMapping("/{id}/right")
    public ResponseEntity<Boolean> saveRight(@PathVariable String id, @RequestBody byte[] request) {
        String data = service.convertByteArrayToString(request);
        return new ResponseEntity<>(service.saveRight(id, data), HttpStatus.CREATED);
    }

    /**
     * Method responsible for returning the diff result for the identification informed.
     *
     * @param id Diff identification
     * @return Response
     */
    @GetMapping("/{id}")
    public ResponseEntity<DiffResponseDTO> getDiff(@PathVariable String id) {
        DiffResponseDTO dto = DiffResponseDTO.convertFromEntity(service.getById(id));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
