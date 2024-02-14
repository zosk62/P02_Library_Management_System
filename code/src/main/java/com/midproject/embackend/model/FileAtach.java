package com.midproject.embackend.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class FileAtach {
    @Id
    @GeneratedValue
    int id;
    String originalName;
    String saveName;

    @ManyToOne
    Comment comment;

}
