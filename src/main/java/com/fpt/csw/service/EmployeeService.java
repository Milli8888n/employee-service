package com.fpt.csw.service;

import com.fpt.csw.model.Employee;
import com.fpt.csw.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository){this.repository=repository;}

    @Transactional(readOnly=true)
    public Employee[] getEmployees(){return repository.findAll().toArray(new Employee[0]);}

    @Transactional
    public Employee addEmployees(Employee employee){employee.setId(null);return repository.save(employee);}

    @Transactional
    public Employee updateEmployee(Employee employee){
        if(employee.getId()==null){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Cần cung cấp id để cập nhật");}
        Employee existing=repository.findById(employee.getId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND,"Không tìm thấy nhân viên có id "+employee.getId()));
        existing.setName(employee.getName());
        existing.setSalary(employee.getSalary());
        return repository.save(existing);
    }
}
