package it.customfanta.be.service;

import it.customfanta.be.model.User;
import it.customfanta.be.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService extends BaseService {

    @Autowired
    private UsersRepository usersRepository;

    public User saveUser(User user) {
        return usersRepository.save(user);
    }

    public User findUser(User findUserRequest) {
        return usersRepository.findByUsernameOrMail(findUserRequest.getUsername(), findUserRequest.getMail()).orElse(null);
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public void deleteAll() {
        usersRepository.deleteAll();
    }

    public User findById(String username) {
        return usersRepository.findById(username).orElse(null);
    }

    public void deleteByID(String username) {
        usersRepository.deleteById(username);
    }

    @Transactional
    public void dropUsers() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS users")
                .executeUpdate();
    }

}
