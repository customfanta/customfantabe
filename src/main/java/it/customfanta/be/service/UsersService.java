package it.customfanta.be.service;

import it.customfanta.be.model.User;
import it.customfanta.be.repository.UsersRepository;
import it.customfanta.be.security.MD5Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public User saveUser(User user) {
        user.setPassword(MD5Security.getMD5Pass(user.getPassword()));
        return usersRepository.save(user);
    }

    public User findUser(User findUserRequest) {
        return usersRepository.findByUsernameAndMail(findUserRequest.getUsername(), findUserRequest.getMail()).orElse(null);
    }

    public User checkUserClear(User findUserRequest) {
        return usersRepository.findByUsernameOrMail(findUserRequest.getUsername(), findUserRequest.getMail()).orElse(null);
    }

}
