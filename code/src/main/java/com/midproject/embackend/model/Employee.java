package com.midproject.embackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Employee {
  @Id
    @GeneratedValue
    int id;
    String firstName;
    String lastName;
    String email;
}
