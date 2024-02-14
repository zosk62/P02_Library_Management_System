package com.midproject.embackend.controller;

import com.midproject.embackend.model.Book;
import com.midproject.embackend.repository.BookRepository;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {

  @Autowired
  BookRepository bookRepository;

  @PostMapping("/uploadFile")
  public String uploadFile(
    @RequestParam("file") MultipartFile mFile,
    @RequestParam("bookId") int bookId
  ) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);
    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      String oName = mFile.getOriginalFilename();
      File folder = new File("c:/files");
      folder.mkdirs();
      File file = new File(folder.getAbsolutePath() + "/" + oName);
      try {
        mFile.transferTo(file);
        book.setImagePath(file.getAbsolutePath());
        bookRepository.save(book);
      } catch (IllegalStateException | IOException e) {
        e.printStackTrace();
      }
      return "redirect:/bookDetail/" + bookId;
    }
    return "error";
  }
}
