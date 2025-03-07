package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Azione;
import it.customfanta.be.service.AzioniService;
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

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    })
            }
    )
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
