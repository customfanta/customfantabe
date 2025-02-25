package it.customfanta.be.controller;

import it.customfanta.be.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class EmptyController {

    private static final Logger logger = Logger.getLogger(EmptyController.class.getName());

    @Autowired
    private UsersService usersService;

    @RequestMapping(method = RequestMethod.GET, value = "/test", produces = { "application/json" })
    public ResponseEntity<String> test() {
        logger.info("API RICHIAMATA");
        usersService.saveNewUser();
        logger.info("SALVATAGGIO EFFETTUATO");
        return ResponseEntity.ok("Test OK");
    }
}
