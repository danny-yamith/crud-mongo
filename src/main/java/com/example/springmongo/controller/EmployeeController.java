package com.example.springmongo.controller;

import com.example.springmongo.dto.EmployeeDTO;
import com.example.springmongo.service.IEmployeeService;
import org.bson.Document;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    private final Logger LOG = LoggerFactory.getLogger(getClass());

    @PostMapping
    public ResponseEntity<EmployeeDTO> saveEmployee(@RequestBody EmployeeDTO employeeDTO, @RequestParam String idDrugstore) {
        employeeService.saveEmployee(employeeDTO, idDrugstore);
        LOG.info("Saving employee.");
        return ResponseEntity.ok(employeeDTO);
    }

    @GetMapping
    public List<EmployeeDTO> getAll() {
        LOG.info("List all employees.");
        return employeeService.findAll();
    }

    @GetMapping("/name/{name}")
    public List<EmployeeDTO> getEmployeeStartWith(@PathVariable String name) {
        LOG.info("List employees by name.");
        return employeeService.getEmployeeStartWith(name);
    }

    @GetMapping("/age")
    public List<EmployeeDTO> getByEmployeeAge(@RequestParam Integer minAge,
                                       @RequestParam Integer maxAge) {
        LOG.info("List employees by.");
        return employeeService.getByEmployeeAge(minAge,maxAge);
    }

    @GetMapping("/oldestEmployee")
    public List<Document> getOldestEmployee() {
        LOG.info("List oldest employees.");
        return employeeService.getOldestEmployeeByCity();
    }

    @GetMapping("/populationByCity")
    public List<Document> getPopulationByCity() {
        LOG.info("List population by city.");
        return employeeService.getPopulationByCity();
    }

    @GetMapping("{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable String id) {
        LOG.info("Employee by Id.");
        return ResponseEntity.ok(employeeService.findEmployeeById(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable String id, @RequestBody EmployeeDTO employeeDTO) {
        employeeService.updateEmployee(id, employeeDTO);
        LOG.info("Update employees.");
        return ResponseEntity.ok(employeeDTO);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployeeById(id);
        LOG.info("Delete employee.");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
