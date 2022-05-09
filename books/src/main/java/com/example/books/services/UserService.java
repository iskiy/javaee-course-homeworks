package com.example.books.services;

import com.example.books.config.MyPasswordEncoder;
import com.example.books.entities.PermissionEntity;
import com.example.books.entities.UserEntity;
import com.example.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final MyPasswordEncoder myPasswordEncoder;

    private final UserRepository userRepository;

    public UserEntity registerUser(UserEntity user) {
//        user.setPassword(myPasswordEncoder.encode(user.getPassword()));
        PermissionEntity permission = userRepository.getFavouritePermission();
        System.out.println(permission);
        user.setPermissions(List.of(permission));
        user.setFavouriteBooks(List.of());
        System.out.println("user: " + user);
        UserEntity userFromDB = userRepository.findByLogin(user.getLogin()).orElse(null);
        if (userFromDB == null) {
            userRepository.saveAndFlush(user);
        }

        userFromDB = userRepository.findByLogin(user.getLogin()).orElse(null);
        System.out.println("IS REGISTERED: " + userFromDB);
        return user;
    }
}
