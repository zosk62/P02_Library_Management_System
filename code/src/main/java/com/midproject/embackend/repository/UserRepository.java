package com.midproject.embackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.midproject.embackend.model.Userb;




public interface UserRepository extends JpaRepository<Userb, Integer> {
  public Userb findByEmailAndPwd(String email, String pwd);

  Userb findByEmail(String email);
}