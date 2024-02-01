package io.github.AndCandido.apispringsecurity.services;

import io.github.AndCandido.apispringsecurity.entities.User;
import io.github.AndCandido.apispringsecurity.entities.repositories.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService, IUserService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveUser(User user) {
        user.setAuthorities(List.of("USER"));
        String userPassword = user.getPassword();
        String passwordEncoded = passwordEncoder.encode(userPassword);
        user.setPassword(passwordEncoded);
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}
