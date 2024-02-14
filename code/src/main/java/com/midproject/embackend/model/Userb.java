package com.midproject.embackend.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Userb {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String email;
	private String pwd;
	private String name;
	private String role;

}