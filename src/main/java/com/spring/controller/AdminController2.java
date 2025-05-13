package com.spring.controller;

import com.spring.model.Item;
import com.spring.model.Product_Detail;
import com.spring.repo.CartIItemRepository;
import com.spring.repo.CartRepository;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class AdminController2 {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ItemReposiotry itemReposiotry;
    @Autowired
    private CartIItemRepository cartIItemRepository;

    @GetMapping("/viewproducts")
   public ModelAndView ProductsTable() {
      List<Product_Detail> allproducts = productRepository.findAll();
      ModelAndView modelAndView = new ModelAndView();
      modelAndView.addObject("products", allproducts);
      modelAndView.setViewName("Products");
      return modelAndView;
    }
    @PostMapping("/deleteproduct")
    public ModelAndView deleteProduct(@RequestParam("name")String name) {
        productRepository.deleteByProdName(name);

       itemReposiotry.deleteByProductName(name);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/viewproducts");
        return modelAndView;

    }


}
