package it.customfanta.be.controller;

import it.customfanta.be.model.FindUserRequest;
import it.customfanta.be.model.User;
import it.customfanta.be.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UsersController {

    private static final Logger logger = Logger.getLogger(UsersController.class.getName());

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST, value = "/find-user", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<User> findUser(@RequestBody FindUserRequest findUserRequest) {
        logger.info("RECEIVED POST /find-user");
        return ResponseEntity.ok(usersService.findUser(findUserRequest));
    }
}
