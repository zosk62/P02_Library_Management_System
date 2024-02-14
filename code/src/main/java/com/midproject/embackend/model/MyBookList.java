package com.midproject.embackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "MyBooks")
public class MyBookList {

  @Id
  int id;

  String name;
  String author;
  String price;
}
