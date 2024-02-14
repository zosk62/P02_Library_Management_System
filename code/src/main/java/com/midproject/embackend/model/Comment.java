package com.midproject.embackend.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class Comment {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  int id;

  String title;
  String content;
  String writer;
  String fileName;
  Date creDate;

  @ManyToOne
  Userb user;

  @ManyToOne
  Book book;

  @OneToMany(mappedBy = "comment")
  List<FileAtach> files = new ArrayList<>();; 
}

