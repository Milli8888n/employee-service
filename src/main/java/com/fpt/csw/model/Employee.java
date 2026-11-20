package com.fpt.csw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
@Table(name="employees")
public class Employee {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Tên nhân viên không được để trống")
    @Column(nullable=false,length=100)
    private String name;

    @NotNull(message="Lương không được để trống")
    @DecimalMin(value="0.0",inclusive=true)
    @Column(nullable=false,precision=12,scale=2)
    private BigDecimal salary;

    public Employee() {}
    public Employee(Long id,String name,BigDecimal salary){this.id=id;this.name=name;this.salary=salary;}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public BigDecimal getSalary(){return salary;} public void setSalary(BigDecimal salary){this.salary=salary;}
}
