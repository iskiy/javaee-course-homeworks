package com.example.books.services;

import com.example.books.entities.PermissionEntity;
import com.example.books.entities.UserEntity;
import com.example.books.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class BookUserDetailsService implements UserDetailsService {
    @Autowired
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        System.out.println(username);
        final UserEntity user = userRepository.findByLogin(username)

                .orElseThrow(() -> new UsernameNotFoundException("No user with login: " + username));
        return User.builder()
                .username(username)
                .password(user.getPassword())
                .authorities(mapAuthorities(user.getPermissions()))
                .build();
    }

    private static List<GrantedAuthority> mapAuthorities(final List<PermissionEntity> permissions) {
        return permissions.stream()
                .map(PermissionEntity::getPermission)
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }
}


