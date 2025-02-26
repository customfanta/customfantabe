package it.customfanta.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class HealthController {

    private static final Logger logger = Logger.getLogger(HealthController.class.getName());

    @RequestMapping(method = RequestMethod.GET, value = "/health", produces = { "application/json" })
    public ResponseEntity<String> health() {
        logger.info("Ricevuta chiamata /health");
        return ResponseEntity.ok("Health Status: OK");
    }
}
