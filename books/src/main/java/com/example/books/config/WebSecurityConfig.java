package com.example.books.config;

import com.example.books.repository.UserRepository;
import com.example.books.services.BookUserDetailsService;
import com.example.books.type.Permission;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
//@RequiredArgsConstructor
//@Configuration
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final UserRepository userRepository;
//
//    @Override
//    protected void configure(final HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/admin", "/admin/**").hasAuthority(Permission.VIEW_ADMIN.name())
//                .antMatchers("/catalog").hasAuthority(Permission.VIEW_CATALOG.name())
//                .antMatchers("/profile").authenticated()
//                .anyRequest().permitAll()
//                .and()
//                .formLogin().permitAll()
//                .and()
//                .logout().permitAll();
//    }
//
//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new MyUserDetailsService(userRepository);
//    }
//}
//


@RequiredArgsConstructor
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/add-favourite").hasAuthority(Permission.VIEW_CATALOG.name())
                .antMatchers("/favourite-delete").hasAuthority(Permission.VIEW_CATALOG.name())
                .antMatchers("/show-favourites").hasAuthority(Permission.VIEW_CATALOG.name())
                .anyRequest().permitAll()
            .and()
            .formLogin().permitAll()
            .and()
            .logout().permitAll();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new BookUserDetailsService(userRepository);
    }
}
