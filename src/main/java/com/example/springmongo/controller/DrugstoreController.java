package com.example.springmongo.controller;

import com.example.springmongo.dto.DrugstoreDTO;
import com.example.springmongo.service.IDrugstoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/drugstores")
public class DrugstoreController {

    @Autowired
    private IDrugstoreService drugstoreService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<DrugstoreDTO> saveDrugstore(@RequestBody DrugstoreDTO drugstoreDTO) {
        drugstoreService.saveDrugstore(drugstoreDTO);
        LOG.info("Saving drugstore.");
        return ResponseEntity.ok(drugstoreDTO);
    }

    @GetMapping
    public List<DrugstoreDTO> getAll() {
        LOG.info("List all drugstores.");
        return drugstoreService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<DrugstoreDTO> getDrugstoreById(@PathVariable String id) {
        LOG.info("Drugstore by Id.");
        return ResponseEntity.ok(drugstoreService.findDrugstoreById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<DrugstoreDTO> updateDrugstore(@PathVariable String id, @RequestBody DrugstoreDTO drugstoreDTO) {
        drugstoreService.updateDrugstore(id, drugstoreDTO);
        LOG.info("Update drugstore.");
        return ResponseEntity.ok(drugstoreDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String id) {
        drugstoreService.deleteDrugstoreById(id);
        LOG.info("Delete drustore.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
