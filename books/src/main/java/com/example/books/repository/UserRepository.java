package com.example.books.repository;

import com.example.books.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

//import java.util.Optional;
//
//public interface UserRepository extends JpaRepository<UserEntity, Integer> {
//
//    @Query("SELECT user FROM UserEntity user "
//            + "LEFT JOIN FETCH user.permissions "
//            + "WHERE user.login = :login")
//    Optional<UserEntity> findByLogin(@Param("login") String login);
//
//}


import com.example.books.entities.PermissionEntity;
import com.example.books.entities.UserEntity;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Query("SELECT user FROM UserEntity user "
            + "LEFT JOIN FETCH user.permissions "
            + "WHERE user.login = :login")
    Optional<UserEntity> findByLogin(@Param("login") String login);

    UserEntity saveAndFlush(UserEntity user);

    @Query("SELECT per FROM PermissionEntity per "
            + "WHERE per.permission ='VIEW_CATALOG'")
    PermissionEntity getFavouritePermission();

}

