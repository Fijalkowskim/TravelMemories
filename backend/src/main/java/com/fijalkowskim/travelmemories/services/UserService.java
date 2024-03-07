package com.fijalkowskim.travelmemories.services;

import com.fijalkowskim.travelmemories.exceptions.CustomHTTPException;
import com.fijalkowskim.travelmemories.repositories.UserDAORepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.fijalkowskim.travelmemories.models.users.User;

@Service
@Transactional
public class UserService {
    private final UserDAORepository userDAORepository;
    @Autowired
    public UserService(UserDAORepository userDAORepository) {
        this.userDAORepository = userDAORepository;
    }
    public User authenticate(String email,String password) throws CustomHTTPException {
        User user = userDAORepository.findByEmail(email);
        if (user == null) throw new CustomHTTPException("There is no user with such email.", HttpStatus.NOT_FOUND);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        if( encoder.matches(password, user.getPasswordHash())) return user;
        throw new CustomHTTPException("Wrong password.", HttpStatus.BAD_REQUEST);
    }
    public void deleteUser(String email, String password) throws CustomHTTPException{
        User user = authenticate(email,password);
        userDAORepository.delete(user);
    }
    public User createUser(String email, String password) throws CustomHTTPException {
        if(userDAORepository.findByEmail(email) != null) throw new CustomHTTPException("User with this email already exists.",HttpStatus.CONFLICT);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(encoder.encode(password));
        return userDAORepository.save(user);
    }
    public void changePassword(String email,String oldPassword, String newPassword) throws CustomHTTPException{
        User user = authenticate(email, oldPassword);
        if(oldPassword.equals(newPassword)) throw new CustomHTTPException("Passwords must differ.",HttpStatus.BAD_REQUEST);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        userDAORepository.updatePasswordHashForUser(user.getEmail(),encoder.encode(newPassword));
    }
}
