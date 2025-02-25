package it.customfanta.be.service;

import it.customfanta.be.repository.User;
import it.customfanta.be.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    public void saveNewUser() {
        User user = new User();
        user.setUsername("Antonio98");
        user.setNome("Antonio");
        user.setProfile("ADMIN");
        usersRepository.save(user);
    }

}
