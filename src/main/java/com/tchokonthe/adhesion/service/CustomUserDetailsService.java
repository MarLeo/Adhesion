package com.tchokonthe.adhesion.service;


import com.tchokonthe.adhesion.model.User;
import com.tchokonthe.adhesion.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
//    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<User> user = userRepository.findByUsername(username);

        AtomicReference<User> result = new AtomicReference<>();

        user.ifPresentOrElse(result::set,
                () -> new UsernameNotFoundException(username + " doesn't exist"));

        return result.get();
    }
}
