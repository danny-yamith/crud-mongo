package com.example.springmongo.service.impl;

import com.example.springmongo.dto.EmployeeDTO;
import com.example.springmongo.exceptions.EmployeeException;
import com.example.springmongo.model.Drugstore;
import com.example.springmongo.model.Employee;
import com.example.springmongo.repository.IEmployeeRepository;
import com.example.springmongo.service.IEmployeeService;
import com.example.springmongo.util.ObjectMapperUtils;
import com.mongodb.BasicDBObject;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.aggregation.UnwindOperation;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<EmployeeDTO> findAll() {
        return ObjectMapperUtils.mapAll(employeeRepository.findAll(), EmployeeDTO.class);
    }

    /*db.drugstore.updateOne(
  { branch: "Drug1" },
  { $push: { employees: ObjectId('61f942b737bdc10018722539') } }
)*/
    @Override
    public void saveEmployee(EmployeeDTO employeeDTO, String idDrugstore) {
        employeeDTO.setIdDrugstore(idDrugstore);
        mongoTemplate.update(Drugstore.class)
                .matching(where("id").is(idDrugstore))
                .apply(new Update().push("employees").value(employeeRepository.save(ObjectMapperUtils.map(employeeDTO, Employee.class))))
                .first();
    }

    @Override
    public void updateEmployee(String id, EmployeeDTO employeeDTO) {
        EmployeeDTO updateEmployee = ObjectMapperUtils.map(employeeRepository.findById(id), EmployeeDTO.class);
        updateEmployee.setName(employeeDTO.getName());
        updateEmployee.setLastName(employeeDTO.getLastName());
        updateEmployee.setEmailId(employeeDTO.getEmailId());
        employeeRepository.save(ObjectMapperUtils.map(updateEmployee, Employee.class));
    }


    /*db.drugstore.updateOne(
    { branch: "Drug1" },
    { $pull: { employees: ObjectId('61f942b737bdc10018722539') } }
    )    */

    @Override
    public void deleteEmployeeById(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeException("Employee by Id " + id + " was not found.", HttpStatus.NOT_FOUND);
        }

        mongoTemplate.updateFirst(new Query(where("id").is(optionalEmployee.get().getIdDrugstore())),
                new Update().pull("employees", new ObjectId(id)), Drugstore.class);
        employeeRepository.deleteById(id);
    }

    @Override
    public EmployeeDTO findEmployeeById(String id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new EmployeeException("Employee by Id " + id + " was not found.", HttpStatus.NOT_FOUND);
        }
        return ObjectMapperUtils.map(optionalEmployee, EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getEmployeeStartWith(String name) {
        return ObjectMapperUtils.mapAll(employeeRepository.findByNameStartsWith(name), EmployeeDTO.class);
    }

    @Override
    public List<EmployeeDTO> getByEmployeeAge(Integer minAge, Integer maxAge) {
        return ObjectMapperUtils.mapAll(employeeRepository.findEmployeeByAgeBetween(minAge, maxAge), EmployeeDTO.class);
    }

    @Override
    public List<Document> getOldestEmployeeByCity() {
        UnwindOperation unwindOperation
                = Aggregation.unwind("addresses");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC, "age");
        GroupOperation groupOperation
                = Aggregation.group("addresses.city")
                .first(Aggregation.ROOT)
                .as("oldestEmployee");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation, sortOperation, groupOperation);

        return mongoTemplate.aggregate(aggregation, Employee.class, Document.class).getMappedResults();
    }

    @Override
    public List<Document> getPopulationByCity() {

        UnwindOperation unwindOperation
                = Aggregation.unwind("addresses");
        GroupOperation groupOperation
                = Aggregation.group("addresses.city")
                .count().as("popCount");
        SortOperation sortOperation
                = Aggregation.sort(Sort.Direction.DESC, "popCount");

        ProjectionOperation projectionOperation
                = Aggregation.project()
                .andExpression("_id").as("city")
                .andExpression("popCount").as("count")
                .andExclude("_id");

        Aggregation aggregation
                = Aggregation.newAggregation(unwindOperation, groupOperation, sortOperation, projectionOperation);
        return mongoTemplate.aggregate(aggregation, Employee.class, Document.class).getMappedResults();
    }
}
