package com.example.springmongo.service;

import com.example.springmongo.dto.EmployeeDTO;
import org.bson.Document;

import java.util.List;

public interface IEmployeeService {
    public List<EmployeeDTO> findAll();

    public void saveEmployee(EmployeeDTO employee, String idDrugstore);

    public void updateEmployee(String id, EmployeeDTO employee);

    public void deleteEmployeeById(String id);

    public EmployeeDTO findEmployeeById(String id) ;

    List<EmployeeDTO> getEmployeeStartWith(String name);

    List<EmployeeDTO> getByEmployeeAge(Integer minAge, Integer maxAge);

    List<Document> getOldestEmployeeByCity();

    List<Document> getPopulationByCity();
}
