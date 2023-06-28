package com.example.springmongo.dto;

import com.example.springmongo.model.Employee;
import lombok.Data;

import java.util.List;

@Data
public class DrugstoreDTO {

    private String id;
    private String branch;
    private String address;
    private List<Employee> employees;
}
