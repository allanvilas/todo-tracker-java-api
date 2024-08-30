package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.model.User;
import app.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    public boolean validateUser(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return false; // User not found
        }
        return passwordEncoder.matches(password, user.getPassword());
    }
}