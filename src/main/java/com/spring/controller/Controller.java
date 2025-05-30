package com.spring.controller;

import com.spring.jwt.Jwt_Util;
import com.spring.model.*;
import com.spring.repo.CartRepository;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.ProductRepository;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    ItemReposiotry repo;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private Jwt_Util jwt_Util;
    @Autowired
    private ItemReposiotry itemReposiotry;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping("/")
    public String index() {
        return "shop";
    }

    @GetMapping("/re-to")
    public String reTo() {
        return "shop";
    }


    @PostMapping("/cart")
    public ModelAndView add(@RequestParam("itemId") long itemid, @CookieValue(value = "jwtToken", required = false) String token) {
        Item item = itemReposiotry.getReferenceById(itemid);
        System.out.println(itemid);
        String username = jwt_Util.extractUsername(token);
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        System.out.println(user.getUsername());
        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartRepository.save(newCart);
        });
//        boolean alreadyAdded = cart.getItemList().stream()
//                .anyMatch(cartItem -> cartItem.getItem().getPrdouct_id() == itemid);
        Product_Detail productDetail = new Product_Detail();
        productDetail = productRepository.findById(itemid).get();
        int q = productDetail.getProd_quantity();
        q = q-1;
        productRepository.updateProductQuantityById(itemid,q);

     //   if (!alreadyAdded) {
            CartItem newItem = new CartItem(); // Assuming a CartItem model for many-to-many relation
            newItem.setItem(item);
            newItem.setCart(cart);
            cart.getItemList().add(newItem);
            System.out.println(newItem.getItem().getPrdouct_id());
            cartRepository.save(cart);
      //  }



        ModelAndView mv = new ModelAndView("Shop");

        mv.addObject("items", cart.getItemList());
        // Show only this user’s cart items
        return mv;

    }


    @GetMapping("/auth/login")
    public String lo() {
        return "login";
    }


    @GetMapping("register")
    public String lol() {
        return "Register";
    }

    @GetMapping("/save")
    public ModelAndView register(@ModelAttribute User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Cart cart = new Cart();
        cart.setUser(user);
        user.setCart(cart);

        User savedUser = userRepository.save(user); // ✅ cascade saves cart too
        ModelAndView mv = new ModelAndView("/shopcart");
        mv.addObject("obj", savedUser);
        return mv;
    }


    @GetMapping("/userCart")
    public ModelAndView viewCart(@CookieValue(value = "jwtToken", required = false) String token) {
        String username = jwt_Util.extractUsername(token);
        User user = userRepository.findByUsernameOrEmailOrPhone(username);
        System.out.println(user.getUsername());
        Cart cart = user.getCart();
        List<CartItem> items = cart.getItemList();
        System.out.println(items);
        ModelAndView mv = new ModelAndView("checkout");
        mv.addObject("items", items);
        mv.addObject("name",username);// you will loop over this in HTML
        return mv;
    }

    @PostMapping("/delete")
    public ModelAndView del() {
        repo.deleteAll();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/shop");
        return mv;
    }

    @GetMapping("/checkout")
    public String gotoCheckout() {
        return "checkout";
    }

}

