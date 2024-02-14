package com.midproject.embackend.controller;

import com.midproject.embackend.model.Userb;
import com.midproject.embackend.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin(origins = "*")
public class HomeController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @GetMapping("/")
  public String mainPage() {
    return "main";
  }

  @GetMapping("/signin")
  public String signin(
    @RequestHeader(required = false, value = "referer") String referer,
    Model model
  ) {
    System.out.println(referer);
    int idx = referer.indexOf("//");
    idx = referer.indexOf("/", idx + 2);
    String path = referer.substring(idx);
    System.out.println(path);
    model.addAttribute("referer", path);
    return "signin";
  }

  @PostMapping("/signin")
  public String signinPost(
    @ModelAttribute Userb user,
    HttpSession session,
    @RequestParam(required = false) String referer
  ) {
    Userb existingUser = userRepository.findByEmail(user.getEmail());
    String encodedPwd = existingUser.getPwd();
    String userPwd = user.getPwd();
    boolean isMatch = passwordEncoder.matches(userPwd, encodedPwd);
    if (isMatch) {
      session.setAttribute("user", existingUser);
      return "redirect:" + referer;
    } else {
      return "redirect:/signin";
    }
  }

  @PostMapping("/react/signin")
  @ResponseBody
  public Map<String, Object> reactSigninPost(
    @ModelAttribute Userb user,
    HttpSession session,
    @RequestParam(required = false) String referer
  ) {
    Userb existingUser = userRepository.findByEmail(user.getEmail());
    String encodedPwd = existingUser.getPwd();
    String userPwd = user.getPwd();
    boolean isMatch = passwordEncoder.matches(userPwd, encodedPwd);

    Map<String, Object> map = new HashMap<>();

    if (isMatch) {
      session.setAttribute("user", existingUser);
      map.put("msg", "Success");
    } else {
      map.put("msg", "Fail");
    }
    return map;
  }

  //   @PostMapping("/signin")
  //   public String signinPost(@ModelAttribute Userb user) {
  //     Userb existingUser = userRepository.findByEmailAndPwd(
  //       user.getEmail(),
  //       user.getPwd()
  //     );
  //     if (existingUser != null) {
  //       return "redirect:/";
  //     } else {
  //       return "redirect:/signin";
  //     }
  //   }

  @GetMapping("/signup")
  public String signup() {
    return "signup";
  }

  @PostMapping("/signup")
  public String signupPost(@ModelAttribute Userb user) {
    String userPwd = user.getPwd();
    String encodedPwd = passwordEncoder.encode(userPwd);
    user.setPwd(encodedPwd);
    userRepository.save(user);
    System.out.println(encodedPwd);
    return "redirect:/signin";
  }

  @GetMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/";
  }
}
