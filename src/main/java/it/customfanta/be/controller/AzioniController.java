package it.customfanta.be.controller;

import it.customfanta.be.model.Azione;
import it.customfanta.be.model.Personaggio;
import it.customfanta.be.service.AzioniService;
import it.customfanta.be.service.PersonaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class AzioniController {

    private static final Logger logger = Logger.getLogger(AzioniController.class.getName());

    @Autowired
    private AzioniService azioniService;

    @RequestMapping(method = RequestMethod.POST, value = "/create-azione", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Void> createAzione(@RequestBody Azione azione, @RequestHeader("profilo") String profilo) throws URISyntaxException {
        logger.info("RECEIVED POST /create-personaggio");
        if("ADMIN".equals(profilo)) {
            azioniService.saveAzione(azione);
            return ResponseEntity.created(new URI("db")).build();
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
    }
}
