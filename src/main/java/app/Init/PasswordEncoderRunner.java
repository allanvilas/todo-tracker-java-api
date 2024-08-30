package app.Init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import app.model.User;
import app.repository.UserRepository;

@Component
public class PasswordEncoderRunner implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        // Retrieve all users
        List<User> users = userRepository.findAll();

        // Iterate through each user and encode their password
        for (User user : users) {
            String rawPassword = user.getPassword(); // Get the plain text password
            String encodedPassword = passwordEncoder.encode(rawPassword); // Encode it
            user.setPassword(encodedPassword); // Set the encoded password
            userRepository.save(user); // Save the user back to the database
        }
    }
}
