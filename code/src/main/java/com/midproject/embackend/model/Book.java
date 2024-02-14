package com.midproject.embackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Book {

  @Id
  @GeneratedValue
  int id;

  String name;
  String author;
  String price;
  String imagePath;
}
