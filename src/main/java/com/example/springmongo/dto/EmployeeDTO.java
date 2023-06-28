package com.example.springmongo.dto;

import com.example.springmongo.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private String id;
    private String name;
    private String lastName;
    private String emailId;
    private Integer age;
    private List<String> hobbies;
    private List<Address> addresses;
    private String  idDrugstore;
}
