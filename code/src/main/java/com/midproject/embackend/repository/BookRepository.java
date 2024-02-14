package com.midproject.embackend.repository;

import com.midproject.embackend.model.Book;
import com.midproject.embackend.model.Comment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
  List<Book> findByAuthor(String author);
  Optional<Book> findById(int id);
}
