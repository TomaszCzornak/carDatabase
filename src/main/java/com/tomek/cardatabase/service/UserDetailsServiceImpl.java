package com.tomek.cardatabase.service;

import com.tomek.cardatabase.domain.UserEntity;
import com.tomek.cardatabase.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findUserEntityByUsername(username);
        User.UserBuilder builder = null;
        if (userEntity.isPresent()) {
            UserEntity currentUser = userEntity.get();
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(currentUser.getPassword());
            builder.roles(currentUser.getRole());



        }else {
            throw new UsernameNotFoundException("User not  found");
        }
        return builder.build();
    }
}
