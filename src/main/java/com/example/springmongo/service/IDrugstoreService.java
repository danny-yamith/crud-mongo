package com.example.springmongo.service;

import com.example.springmongo.dto.DrugstoreDTO;

import java.util.List;

public interface IDrugstoreService {
    public List<DrugstoreDTO> findAll();

    public void saveDrugstore(DrugstoreDTO drugstore);

    public void updateDrugstore(String id, DrugstoreDTO drugstore);

    public void deleteDrugstoreById(String id);

    public DrugstoreDTO findDrugstoreById(String id) ;
  }
