package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Personaggio;
import it.customfanta.be.service.PersonaggiService;
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
public class PersonaggiController {

    private static final Logger logger = Logger.getLogger(PersonaggiController.class.getName());

    @Autowired
    private PersonaggiService personaggiService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Void.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-personaggio", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Void> createPersonaggio(@RequestBody Personaggio personaggio, @RequestHeader("profilo") String profilo) throws URISyntaxException {
        logger.info("RECEIVED POST /create-personaggio");
        if("ADMIN".equals(profilo)) {
            personaggiService.savePersonaggio(personaggio);
            return ResponseEntity.created(new URI("db")).build();
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Personaggio.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-personaggi", produces = { "application/json" })
    public ResponseEntity<List<Personaggio>> readPersonaggi() {
        logger.info("RECEIVED GET /read-personaggi");

        return ResponseEntity.ok(personaggiService.readPersonaggi());
    }
}
