package com.newspaper.newspaper.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.newspaper.newspaper.exception.EntityNotFoundException;
import com.newspaper.newspaper.model.User;
import com.newspaper.newspaper.repository.UserRepository;


@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User getUser(int id){
        Optional<User> user = userRepository.findById(id); 
        return unwrapUser(user);
    }

    public String deleteUser(int id){
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()) return "User does not exist";
        userRepository.deleteById(id);
        return (unwrapUser(user).getName() + " deleted successfully");
    }

    public static User unwrapUser(Optional<User> entity){
        if(entity.isPresent()) return entity.get();
        else throw new EntityNotFoundException(User.class);
    }
    
}
