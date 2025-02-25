package it.customfanta.be.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class EmptyController {

    @RequestMapping(method = RequestMethod.GET, value = "/test", produces = { "application/json" })
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test OK");
    }
}
