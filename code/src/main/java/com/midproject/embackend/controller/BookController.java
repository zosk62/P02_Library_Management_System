package com.midproject.embackend.controller;

import com.midproject.embackend.model.Book;
import com.midproject.embackend.model.Comment;
import com.midproject.embackend.model.FileAtach;
import com.midproject.embackend.model.MyBookList;
import com.midproject.embackend.model.Userb;
import com.midproject.embackend.repository.BookRepository;
import com.midproject.embackend.repository.CommentRepository;
import com.midproject.embackend.repository.FileAtachRepository;
import com.midproject.embackend.repository.MyBookRepository;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BookController {

  @Autowired
  BookRepository bookRepository;

  @Autowired
  MyBookRepository myBookRepository;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  FileAtachRepository fileAtachRepository;

  @Autowired
  HttpSession session;

  @GetMapping("/home")
  public String home() {
    return "home";
  }

  @GetMapping("/book_register")
  public String bookRegister() {
    return "bookRegister";
  }

  @PostMapping("/save")
  public String addBook(@ModelAttribute Book bookb) {
    bookRepository.save(bookb);
    return "redirect:/available_books";
  }

  @GetMapping("/available_books")
  public String booklist(Model model) {
    List<Book> bookl = bookRepository.findAll();
    model.addAttribute("book", bookl);
    return "bookList";
  }

  @GetMapping("/deleteAvailableBook/{id}")
  public String deleteAvailableBook(@PathVariable int id) {
    bookRepository.deleteById(id);
    return "redirect:/available_books";
  }

  @GetMapping("/my_books")
  public String getMyBooks(Model model) {
    List<MyBookList> list = myBookRepository.findAll();
    model.addAttribute("book", list);
    return "myBooks";
  }

  @GetMapping("/mylist/{id}")
  public String addBookToMyList(@PathVariable("id") int id) {
    Optional<Book> optionalBook = bookRepository.findById(id);
    if (optionalBook.isPresent()) {
      Book b = optionalBook.get();
      MyBookList mb = new MyBookList();
      mb.setId(b.getId());
      mb.setName(b.getName());
      mb.setAuthor(b.getAuthor());
      mb.setPrice(b.getPrice());
      myBookRepository.save(mb);
    }

    return "redirect:/my_books";
  }

  /*
   * @PostMapping("/mylist/{id}")
   * public String addBookToMyList(@PathVariable("id") int id) {
   * Optional<Book> optionalBook = bookRepository.findById(id);
   * if (optionalBook.isPresent()) {
   * Book b = optionalBook.get();
   * MyBookList mb = new MyBookList();
   * mb.setId(b.getId());
   * mb.setName(b.getName());
   * mb.setAuthor(b.getAuthor());
   * mb.setPrice(b.getPrice());
   * myBookRepository.save(mb);
   * }
   * return "redirect:/book/my_books";
   * }
   */

  @GetMapping("/deleteMyList/{id}")
  public String deleteBook(@PathVariable int id) {
    myBookRepository.deleteById(id);
    return "redirect:/my_books";
  }

  @GetMapping("/editBook/{id}")
  public String editBook(@PathVariable int id, Model model) {
    Optional<Book> optionalBook = bookRepository.findById(id);

    if (optionalBook.isPresent()) {
      Book b = optionalBook.get();
      model.addAttribute("book", b);
      return "bookEdit";
    } else {
      return "redirect:/available_books";
    }
  }

  /*
   * @PostMapping("/deleteMyList/{id}")
   * public String deleteBook(@PathVariable int id) {
   * myBookRepository.deleteById(id);
   * return "redirect:/book/my_books";
   * }
   */

  @GetMapping("/bookDetail/{id}")
  public String bookDetail(@PathVariable("id") int id, Model model) {
    Optional<Book> optionalBook = bookRepository.findById(id);

    if (optionalBook.isPresent()) {
      Book book = optionalBook.get();
      model.addAttribute("book", book);
      List<Comment> comments = commentRepository.findByBook(book);
      model.addAttribute("comments", comments);

      Userb user = (Userb) session.getAttribute("user");
      if (user != null) {
        model.addAttribute("user", user);
      }

      return "bookDetail";
    } else {
      return "error";
    }
  }

  // @PostMapping("/comment")
  // public String comment(
  // @ModelAttribute Comment comment,
  // @RequestParam int bookId
  // ) {
  // String name = (String) session.getAttribute("name");
  // if (name == null) {
  // name = "Anonymous";
  // }
  // comment.setWriter(name);
  // Optional<Book> optionalBook = bookRepository.findById(bookId);
  // if (optionalBook.isPresent()) {
  // Book book = optionalBook.get();
  // comment.setBook(book);
  // commentRepository.save(comment);

  // return "redirect:/bookDetail/" + bookId;
  // } else {
  // return "redirect:/home";
  // }
  // }
  //%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  // @PostMapping("/comment")
  // public String comment(
  //     @RequestParam("title") String title,
  //     @RequestParam("content") String content,
  //     @RequestParam("files") MultipartFile[] files,
  //     @RequestParam int bookId) {

  //   Userb currentUser = (Userb) session.getAttribute("user");

  //   if (currentUser == null) {
  //     return "redirect:/signin";
  //   }

  //   Comment comment = new Comment();
  //   comment.setTitle(title);
  //   comment.setContent(content);
  //   comment.setWriter(currentUser.getName());
  //   comment.setUser(currentUser);

  //   Optional<Book> optionalBook = bookRepository.findById(bookId);

  //   if (optionalBook.isPresent()) {
  //     Book book = optionalBook.get();
  //     comment.setBook(book);

  //     List<FileAtach> fileAttaches = new ArrayList<>();

  //     for (MultipartFile file : files) {
  //       FileAtach fileAtach = new FileAtach();

  //       fileAtach.setOriginalName(file.getOriginalFilename());

  //       String savePath = saveFileToDisk(file);

  //       fileAtach.setSaveName(savePath);

  //       /* Associate each FileAttach with current Book */

  //       FileAtach.setBook(comment.getBook());
  //       fileAttach.setComment(comment);
  //       files.add(fileAttach);

  //     }

  //     comment.setFiles(files);
  //     commentRespository.save(comment);
  //     return "redirect:/bookDetail/" + bookId;
  //   } else {
  //     return "redirect:/home";
  //   }
  // }

  // private String saveFileToDisk(MultipartFile multipartFile) {

  // }

  @PostMapping("/comment")
  public String comment2(
    @RequestParam("title") String title,
    @RequestParam("content") String content,
    @RequestParam("files") MultipartFile[] files,
    @RequestParam int bookId
  ) {
    Userb currentUser = (Userb) session.getAttribute("user");

    if (currentUser == null) {
      return "redirect:/signin";
    }

    Comment comment = new Comment();
    comment.setTitle(title);
    comment.setContent(content);
    comment.setWriter(currentUser.getName());
    comment.setUser(currentUser);
    Book book = new Book();
    book.setId(bookId);
    comment.setBook(book);

    Comment savedComment = commentRepository.save(comment); // save comment
    //      comment의 기본키 (id)

    // 파일 업로드 관련 동작

    for (MultipartFile file : files) {
      String fileName = file.getOriginalFilename();
      try {
        // 1) 실제 파일이 서버에 저장 transferTo()
        file.transferTo(new File("c:/files/" + fileName));

        // 2) 파일의 경로와 파일의 이름이 FileAtach 테이블에 저장
        FileAtach f = new FileAtach();
        f.setOriginalName(fileName);
        f.setSaveName(fileName);
        f.setComment(savedComment);
        fileAtachRepository.save(f);
      } catch (IllegalStateException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    // Optional<Book> optionalBook = bookRepository.findById(bookId);

    // if (optionalBook.isPresent()) {
    //   Book book = optionalBook.get();
    //   comment.setBook(book);

    List<FileAtach> fileAttaches = new ArrayList<>();
    // }
    return "redirect:/bookDetail/" + bookId;
  }
}
