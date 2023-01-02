package com.mingati.kikunditestrepo.configuration;

import com.mingati.kikunditestrepo.base.UserBo;
import com.mingati.kikunditestrepo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class MyDatabaseUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserBo foundUser= userRepository.findByEmail(username).get();
        if (foundUser.getEmail().equals(username)) {
            return new User(foundUser.getEmail(),
                    foundUser.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}
