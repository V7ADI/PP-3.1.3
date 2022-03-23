package springboot.SpringBoot.service;

import javassist.NotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import springboot.SpringBoot.dao.UserRepository;
import springboot.SpringBoot.model.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    private BCryptPasswordEncoder bCrypt() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getListUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getById(int id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public void update(User user) {
        user.setPassword(bCrypt().encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void delete(int id) {
        Optional<User> optional = userRepository.findById(id);
        optional.ifPresent(userRepository::delete);
    }

    @Override
    public User getByName(String userName) throws NotFoundException {
        User user = userRepository.findByUsername(userName);
        if (user == null) {
            throw new NotFoundException(userName);
        }
        return user;
    }

    @Override
    public User getByEmail(String email) throws NotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new NotFoundException(email);
        }
        return user;
    }
}
