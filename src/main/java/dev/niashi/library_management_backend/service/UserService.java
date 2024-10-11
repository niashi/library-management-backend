package dev.niashi.library_management_backend.service;

import dev.niashi.library_management_backend.model.User;
import dev.niashi.library_management_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public User getUserById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + id));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User addUser(User user) {
        return repository.save(user);
    }

    public User updateUserById(Long id, Map<String, Object> newInfo) {
        User user = getUserById(id);

        List<String> allowedFields = List.of(
                "name",
                "document",
                "email",
                "phone",
                "address"
        );

        for (String key : newInfo.keySet()) {
            if (!allowedFields.contains(key)) {
                throw new IllegalArgumentException(key + ": not a valid field.");
            }

            if (newInfo.get(key) != null) {
                switch (key) {
                    case "name":
                        user.setName((String) newInfo.get(key));
                        break;
                    case "document":
                        user.setDocument((String) newInfo.get(key));
                        break;
                    case "email":
                        user.setEmail((String) newInfo.get(key));
                        break;
                    case "phone":
                        user.setPhone((String) newInfo.get(key));
                        break;
                    case "address":
                        user.setAddress((String) newInfo.get(key));
                        break;
                }
            }
        }

        return addUser(user);
    }

    public void deleteUserById(Long id) {
        User user = getUserById(id);

        repository.deleteById(user.getId());
    }
}
