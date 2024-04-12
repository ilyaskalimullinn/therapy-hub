package ru.itis.therapy.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itis.therapy.exception.UserNotFoundException;
import ru.itis.therapy.model.User;
import ru.itis.therapy.repository.UserRepository;
import ru.itis.therapy.security.details.UserDetailsImpl;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UserNotFoundException("User with this email not found");
        }

        return new UserDetailsImpl(user.get());
    }
}
