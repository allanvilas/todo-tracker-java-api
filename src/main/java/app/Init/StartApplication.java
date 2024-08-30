package app.Init;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import app.model.Role;
import app.model.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import jakarta.transaction.Transactional;

@Component
public class StartApplication implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        
        // Check if the roles already exist in the database
        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        Role usersRole = roleRepository.findByName("ROLE_USERS");

        // If roles don't exist, create and save them
        if (adminRole == null) {
            adminRole = new Role("ROLE_ADMIN");
            roleRepository.save(adminRole);
        }

        if (usersRole == null) {
            usersRole = new Role("ROLE_USERS");
            roleRepository.save(usersRole);
        }

        User user = userRepository.findByUsername("admin");
        if (user==null) {
                user = new User();
                user.setName("ADMIN");
                user.setUsername("admin");
                user.setPassword("admin123");
                user.getRoles().add(adminRole);
                
                userRepository.save(user);
        }
        
        user = userRepository.findByUsername("admin");

        if (user==null) {
                user = new User();
                user.setName("USER");
                user.setUsername("user");
                user.setPassword("user123");
                user.getRoles().add(usersRole);
                
                userRepository.save(user);
        }
        
    }

}
