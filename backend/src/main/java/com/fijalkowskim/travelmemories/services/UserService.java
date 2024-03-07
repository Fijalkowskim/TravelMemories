package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.User;

@Service
@Transactional
public class UserService {
    private final UserDAORepository userDAORepository;
    @Autowired
    public UserService(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }
    public User authenticate(String email,String password) throws Exception{
        User user = userDAORepository.findByEmail(email);
        if (user == null) throw new Exception("There is no user with such email.");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if( encoder.matches(password, user.getPasswordHash())) return user;
        throw new Exception("Wrong password.");
    }
    public void deleteUser(String email, String password) throws Exception{
        User user = authenticate(email,password);
        userDAORepository.delete(user);
    }
    public User createUser(String email, String password) throws Exception {
        if(userDAORepository.findByEmail(email) != null) throw new Exception("User with this email already exists.");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        return userDAORepository.save(user);
    }
    public User findByEmail(String email){
        return userDAORepository.findByEmail(email);
    }

    public void changePassword(String email,String oldPassword, String newPassword) throws Exception{
        User user = authenticate(email, oldPassword);
        if(oldPassword.equals(newPassword)) throw new Exception("Passwords must differ.");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        userDAORepository.updatePasswordHashForUser(user.getEmail(),encoder.encode(newPassword));
    }
}
