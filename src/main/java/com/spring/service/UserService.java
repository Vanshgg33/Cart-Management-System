package com.spring.service;

import com.spring.model.User;
import com.spring.model.UserShow;
import com.spring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserService implements UserDetailsService {
@Autowired
private UserRepository userepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u1 = userepo.findByUsernameOrEmailOrPhone(username);
        if(u1 == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

 return  new UserShow(u1);
    }

}
