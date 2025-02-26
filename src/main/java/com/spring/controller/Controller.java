package com.spring.controller;

import com.spring.model.Item;
import com.spring.model.User;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
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

    @GetMapping("/")
    public String index() {
        return "shop";
    }

    @PostMapping ("/cart")
    public ModelAndView add(Item item) {
        repo.save(item);
        ModelAndView mv = new ModelAndView();
        mv.addObject("obj", item);
        mv.setViewName("Shopcart");

        List<Item> itemlist = repo.findAll();  // Fetch all items from the database

        mv.addObject("items", itemlist);
        System.out.println(itemlist);
return mv;
    }

    @GetMapping("/login")
    public String lo(){
        return "login";
    }

    @GetMapping("/Register")
    public ModelAndView register(User user){
        userRepository.save(user);
        ModelAndView mv = new ModelAndView();
        mv.addObject("obj",user);
        mv.setViewName("login");
        return mv;
    }

}

