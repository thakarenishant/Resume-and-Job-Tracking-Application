package org.web.resumeandjobtracking.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.web.resumeandjobtracking.DTO.LoginRequest;
import org.web.resumeandjobtracking.DTO.UserRequest;
import org.web.resumeandjobtracking.Model.User;
import org.web.resumeandjobtracking.repo.UserRepository;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(UserRequest request) {
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userRepository.save(user);
    }

    public boolean loginUser(LoginRequest user) {
        System.out.println("login method called");
        User user1 =userRepository.findByEmail(user.getEmail()).orElse(null);
        if(user1==null){
            return false;
        }

     return passwordEncoder.matches(user.getPassword(),user1.getPassword());
    }
}

