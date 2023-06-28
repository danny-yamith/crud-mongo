package com.example.springmongo.repository;

import com.example.springmongo.model.Drugstore;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IDrugstoreRepository extends MongoRepository<Drugstore, String> {
}
