package com.fpt.csw;

import com.fpt.csw.model.Employee;
import com.fpt.csw.repository.EmployeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private EmployeeRepository repository;

    @BeforeEach
    void cleanDatabase() {
        repository.deleteAll();
    }

    @Test
    void getEmployeesReturnsArray() throws Exception {
        repository.save(new Employee(null,"Test Employee",new BigDecimal("10000000")));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].name").value("Test Employee"));
    }

    @Test
    void addEmployeesCreatesEmployee() throws Exception {
        Employee employee = new Employee(null,"New Employee",new BigDecimal("22000000"));

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("New Employee"))
                .andExpect(jsonPath("$.salary").value(22000000));
    }

    @Test
    void updateEmployeeUpdatesExistingEmployee() throws Exception {
        Employee employee = repository.save(new Employee(null,"Old Name",new BigDecimal("15000000")));
        Employee updated = new Employee(employee.getId(),"Updated Name",new BigDecimal("25000000"));

        mockMvc.perform(put("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.salary").value(25000000));
    }

    @Test
    void updateEmployeeReturns404WhenIdDoesNotExist() throws Exception {
        Employee missing = new Employee(999999L,"Missing",new BigDecimal("1000000"));

        mockMvc.perform(put("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(missing)))
                .andExpect(status().isNotFound());
    }

    @Test
    void addEmployeesRejectsEmptyName() throws Exception {
        Employee invalid = new Employee(null,"  ",new BigDecimal("1000000"));

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }
}
