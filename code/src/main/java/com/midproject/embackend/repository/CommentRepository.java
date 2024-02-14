package com.midproject.embackend.repository;

import com.midproject.embackend.model.Book;
import com.midproject.embackend.model.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
  List<Comment> findByBook(Book book);
}
