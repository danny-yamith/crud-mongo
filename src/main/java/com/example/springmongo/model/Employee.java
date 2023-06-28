package com.example.springmongo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "employee")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Employee {

    @Id
    private String id;
    private String name;
    private String lastName;
    private String emailId;
    private Integer age;
    private List<String> hobbies;
    private List<Address> addresses;
    private String  idDrugstore;
}

