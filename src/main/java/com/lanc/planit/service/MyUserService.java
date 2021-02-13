package com.lanc.planit.service;

import com.lanc.planit.dao.MyUserRepo;
import com.lanc.planit.model.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Optional;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepo repo;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final Optional<MyUser> optionalUser = repo.findByName(s);

        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        else {
            throw new UsernameNotFoundException(MessageFormat.format("User with name {0} cannot be found.", s));
        }
    }

    public boolean doesUserExist(String name){
        Optional<MyUser> optionalUser = repo.findByName(name);

        return optionalUser.isPresent();
    }

    public void signUpUser(MyUser user) {
        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        repo.save(user);
    }
}
