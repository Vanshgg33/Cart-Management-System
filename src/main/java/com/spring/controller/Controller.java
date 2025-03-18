package com.spring.controller;

import com.spring.model.Item;
import com.spring.model.User;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;



@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    ItemReposiotry repo;
    @Autowired
    UserRepository userRepository;
@Autowired
private PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String index() {
        return "shop";
    }





    @PostMapping ("/cart")
    public ModelAndView add(Item item) {
        repo.save(item);
        ModelAndView mv = new ModelAndView();
        mv.addObject("obj", item);
        mv.setViewName("shopcart");

        List<Item> itemlist = repo.findAll();  // Fetch all items from the database

        mv.addObject("items", itemlist);
        System.out.println("item added");
return mv;
    }

@GetMapping("/delete")
public String h(){
        return "/";
}

    @GetMapping("/auth/login")
    public String lo(){
        return "login";
    }


    @GetMapping("register")
    public String lol(){
        return "Register";
    }

    @GetMapping("/save")
    public ModelAndView register(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        ModelAndView mv = new ModelAndView();
        mv.addObject("obj",user);
        mv.setViewName("/shopcart");
        return mv;
    }
    @PostMapping("/delete")
    public ModelAndView del(){
      repo.deleteAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/shop");
        return mv;
    }

}

