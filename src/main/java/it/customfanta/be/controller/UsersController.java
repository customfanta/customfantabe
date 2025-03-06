package it.customfanta.be.controller;

import it.customfanta.be.model.User;
import it.customfanta.be.security.MD5Security;
import it.customfanta.be.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class UsersController {

    private static final Logger logger = Logger.getLogger(UsersController.class.getName());

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.POST, value = "/make-login", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<User> makeLogin(@RequestBody User userLogin) {
        logger.info("RECEIVED POST /make-login");
        User user = usersService.findUser(userLogin);
        if(user != null) {
            if(MD5Security.getMD5Pass(userLogin.getPassword()).equals(user.getPassword())) {
                user.setPassword(null);
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-user", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Void> createUser(@RequestBody User user) throws URISyntaxException {
        logger.info("RECEIVED POST /create-user");
        if(usersService.checkUserClear(user) != null) {
            return ResponseEntity.badRequest().build();
        }
        user.setProfile("BASIC");
        user.setPassword(MD5Security.getMD5Pass(user.getPassword()));
        usersService.saveUser(user);
        return ResponseEntity.created(new URI("db")).build();
    }


    @RequestMapping(method = RequestMethod.GET, value = "/read-all-user", produces = { "application/json" })
    public ResponseEntity<List<User>> readUsers() {
        logger.info("RECEIVED GET /read-all-user");
        return ResponseEntity.ok(usersService.findAll());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/delete-all-user", produces = { "application/json" })
    public ResponseEntity<Void> deleteUsers() {
        logger.info("RECEIVED GET /delete-all-user");
        usersService.deleteAll();
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/create-admin-user", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Void> createAdminUser(@RequestBody User user) throws URISyntaxException {
        logger.info("RECEIVED POST /create-admin-user");
        if(usersService.checkUserClear(user) != null) {
            return ResponseEntity.badRequest().build();
        }
        user.setProfile("ADMIN");
        user.setPassword(MD5Security.getMD5Pass(user.getPassword()));
        usersService.saveUser(user);
        return ResponseEntity.created(new URI("db")).build();
    }
}
