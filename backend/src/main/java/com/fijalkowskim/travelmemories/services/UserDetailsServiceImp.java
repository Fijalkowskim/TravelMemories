package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserDAORepository userDAORepository;

    @Autowired
    public UserDetailsServiceImp(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDAORepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}