package dev.niashi.library_management_backend.service;

import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUser(Long id) {
        Optional<User> user = repository.findById(id);

        return user.get();
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }
}
