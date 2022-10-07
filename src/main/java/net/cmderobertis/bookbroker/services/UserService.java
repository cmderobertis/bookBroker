package net.cmderobertis.bookbroker.services;
import net.cmderobertis.bookbroker.models.LoginUser;
import org.mindrot.jbcrypt.BCrypt;
import net.cmderobertis.bookbroker.models.User;
import net.cmderobertis.bookbroker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository repo;

    public User register(User user, BindingResult result) {
        if (repo.findByEmail(user.getEmail()).isPresent()) {
            result.rejectValue("email", "Taken", "Email is already in use");
        }
        if (!user.getPassword().equals(user.getConfirm())) {
            result.rejectValue("confirm", "Matches", "Passwords must match");
        }
        if (result.hasErrors()) {
            return null;
        }
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        repo.save(user);
        return user;
    }
    public User login(LoginUser newLoginObject, BindingResult result) {
        Optional<User> userInDB = repo.findByEmail(newLoginObject.getEmail());
        if (!userInDB.isPresent()) {
            result.rejectValue("email", "Unknown", "Invalid Email");
        } else {
            if (!BCrypt.checkpw(newLoginObject.getPassword(), userInDB.get().getPassword())) {
                result.rejectValue("password", "Matches", "Invalid Password");
            }
        }
        if (result.hasErrors()) {
            return null;
        }
        return userInDB.get();
    }
    // CRUD Methods
    public List<User> getAll() {
        return repo.findAll();
    }
    public User getOne(Long id) {
        Optional<User> user = repo.findById(id);
        return user.orElse(null);
    }
    public void update(User user) { repo.save(user); }
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
