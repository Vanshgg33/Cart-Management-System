package com.spring.model;

import lombok.Data;

@Data
 public class LoginRequest {
    private String logininput;
    private String password;
    private String email;
    private String phone;


     public LoginRequest(String password, String email, String phone, String logininput) {
         this.password = password;
         this.email = email;
         this.phone = phone;
         this.logininput = logininput;
     }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "logininput='" + logininput + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}


