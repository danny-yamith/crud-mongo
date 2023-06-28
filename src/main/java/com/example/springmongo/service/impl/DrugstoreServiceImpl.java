package com.example.springmongo.service.impl;

import com.example.springmongo.dto.DrugstoreDTO;
import com.example.springmongo.exceptions.DrugstoreException;
import com.example.springmongo.model.Drugstore;
import com.example.springmongo.repository.IDrugstoreRepository;
import com.example.springmongo.service.IDrugstoreService;
import com.example.springmongo.util.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DrugstoreServiceImpl implements IDrugstoreService {

    @Autowired
    private IDrugstoreRepository drugstoreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<DrugstoreDTO> findAll() {
        return ObjectMapperUtils.mapAll(drugstoreRepository.findAll(), DrugstoreDTO.class);
    }

    @Override
    public void updateDrugstore(String id, DrugstoreDTO drugstore) {
        DrugstoreDTO updateDrugstore = ObjectMapperUtils.map(drugstoreRepository.findById(id), DrugstoreDTO.class);
        updateDrugstore.setBranch(drugstore.getBranch());
        updateDrugstore.setAddress(drugstore.getAddress());
        drugstoreRepository.save(ObjectMapperUtils.map(updateDrugstore, Drugstore.class));

    }

    @Override
    public void deleteDrugstoreById(String id) {
       /* Optional<Drugstore> optionalDrugstore = drugstoreRepository.findById(id);
        if (optionalDrugstore.isEmpty()) {
            throw new DrugstoreException("Drugstore by Id " + id + " was not found.", HttpStatus.NOT_FOUND);
        }
        drugstoreRepository.deleteById(id);*/
    }

    @Override
    public DrugstoreDTO findDrugstoreById(String id) {
        Optional<Drugstore> optionalDrugstore = drugstoreRepository.findById(id);
        if (optionalDrugstore.isEmpty()) {
            throw new DrugstoreException("Drugstore by Id " + id + " was not found.", HttpStatus.NOT_FOUND);
        }
        return ObjectMapperUtils.map(optionalDrugstore, DrugstoreDTO.class);
    }

    @Override
    public void saveDrugstore(DrugstoreDTO drugstoreDTO) {
        drugstoreRepository.save(ObjectMapperUtils.map(drugstoreDTO, Drugstore.class));
    }
}
