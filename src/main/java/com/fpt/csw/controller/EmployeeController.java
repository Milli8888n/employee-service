package com.fpt.csw.controller;

import com.fpt.csw.model.Employee;
import com.fpt.csw.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService service;
    public EmployeeController(EmployeeService service){this.service=service;}

    @GetMapping
    public Employee[] getEmployees(){return service.getEmployees();}

    @PostMapping
    public ResponseEntity<Employee> addEmployees(@Valid @RequestBody Employee employee){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.addEmployees(employee));
    }

    @PutMapping
    public Employee updateEmployee(@Valid @RequestBody Employee employee){return service.updateEmployee(employee);}
}
