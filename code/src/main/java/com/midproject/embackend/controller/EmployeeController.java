package com.midproject.embackend.controller;

import com.midproject.embackend.model.Employee;
import com.midproject.embackend.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
  @Autowired
  HttpSession session;
  @Autowired
  EmployeeRepository employeeRepository;

  @GetMapping("/add")
  public String showEmployeeForm() {
    return "employee-form";
  }

  @PostMapping("/add")
  public String addEmployee(@ModelAttribute Employee employee) {
    employeeRepository.save(employee);
    return "redirect:/employee/add";
  }

  @GetMapping("/list")
  public String listEmployees(Model model) {
    List<Employee> employees = employeeRepository.findAll();
    model.addAttribute("employees", employees);
    return "employee-list";
  }

  @PostMapping("/delete/{id}")
  public String deleteEmployee(@PathVariable int id) {
    employeeRepository.deleteById(id);
    return "redirect:/employee/list";
  }

  @GetMapping("/edit/{id}")
  public String showUpdateForm(@PathVariable("id") int id, Model model) {
    Optional<Employee> employee = employeeRepository.findById(id);
    if (!employee.isPresent()) {
      return "redirect:/employees/list";
    }

    model.addAttribute("employee", employee.get());
    return "update-employee";
  }

  @PostMapping("/update")
  public String updateEmployee(
    @ModelAttribute("employee") Employee employee,
    BindingResult result
  ) {
    if (result.hasErrors()) {
      return "update-employee";
    }

    employeeRepository.save(employee);
    return "redirect:/employee/list";
  }
}
