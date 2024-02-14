package com.midproject.embackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midproject.embackend.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{
  
}
