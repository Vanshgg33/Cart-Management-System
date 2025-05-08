package com.spring.controller;

import com.spring.model.Item;
import com.spring.model.Product_Detail;
import com.spring.repo.ItemReposiotry;
import com.spring.repo.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemReposiotry itemRepository;

    @GetMapping("/AdminProduct")
    public String page() {
        return "Admin";
    }

    @Value("${upload.dir}")
    private String uploadDir;

    @PostMapping("/admin_add")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> addProduct(@RequestParam("name") String name,
                                                          @RequestParam("description") String description,
                                                          @RequestParam("price") int price,
                                                          @RequestParam("quantity") int quantity,
                                                          @RequestParam("image") MultipartFile imageFile) {
        try {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path imagePath = Paths.get(uploadDir, fileName);
            Files.copy(imageFile.getInputStream(), imagePath);

            Product_Detail product = new Product_Detail();
            product.setProd_name(name);
            product.setProd_description(description);
            product.setProd_price(price);
            product.setProd_quantity(quantity);
            product.setProd_image(fileName);
            productRepository.save(product);
            Item newItem = new Item();
            newItem.setPrdouct_id(product.getProd_id());
            newItem.setProductName(product.getProd_name());
            newItem.setPrice(product.getProd_price());
            itemRepository.save(newItem);
            // Return JSON
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("id", product.getProd_id());
            responseBody.put("name", name);
            responseBody.put("description", description);
            responseBody.put("price", price);
            responseBody.put("quantity", quantity);
            responseBody.put("image", fileName);

            return ResponseEntity.ok(responseBody);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Failed to add product"));
        }
    }

    @GetMapping("/products")
    @ResponseBody
    public List<Product_Detail> getAllProducts() {
        return productRepository.findAll();
    }

}




