package app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/","/login","/logout").permitAll()
            .requestMatchers("/tasks","/tasks/**")
                .hasAnyRole("ADMIN","USERS")
            .anyRequest().authenticated() // Allow all requests temporarily
        )
        .formLogin((form) -> form
            .defaultSuccessUrl("/swagger-ui.html", true)  // Redirect to a default page upon successful login
        )
        .logout((logout) -> logout
                .deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
        )
        .csrf(csrf -> csrf.disable()); // Disable CSRF for testing

    return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService testOnlyUsers(PasswordEncoder passwordEncoder) {
        User.UserBuilder users = User.builder();
        UserDetails admin = users
                .username("ADMIN")
                .password(passwordEncoder.encode("1234"))
                .roles("ADMIN")
                .build();
        
        UserDetails user = users
                .username("user")
                .password(passwordEncoder.encode("5678"))
                .roles("USERS")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }
}
