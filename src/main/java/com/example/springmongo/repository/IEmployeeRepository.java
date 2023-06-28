package com.example.springmongo.repository;

import com.example.springmongo.model.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IEmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findByNameStartsWith(String name);
    List<Employee> findEmployeeByAgeBetween(Integer min, Integer max);
}
