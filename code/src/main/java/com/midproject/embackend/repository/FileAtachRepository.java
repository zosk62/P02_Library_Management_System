package com.midproject.embackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.midproject.embackend.model.FileAtach;

@Repository
public interface FileAtachRepository extends JpaRepository<FileAtach, Integer> {
    
}
