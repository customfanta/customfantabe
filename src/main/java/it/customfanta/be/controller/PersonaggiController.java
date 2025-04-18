package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.model.Personaggio;
import it.customfanta.be.model.request.CreatePersonaggioRequest;
import it.customfanta.be.service.PersonaggiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "https://customfantabe.web.app", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class PersonaggiController extends BaseController {

    private static final Logger logger = Logger.getLogger(PersonaggiController.class.getName());

    @Autowired
    private PersonaggiService personaggiService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-personaggio", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createPersonaggio(@RequestBody CreatePersonaggioRequest createPersonaggioRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /create-personaggio");
        Personaggio personaggio = new Personaggio();

        personaggio.setNominativo(createPersonaggioRequest.getNominativo());
        personaggio.setDescrizione(createPersonaggioRequest.getDescrizione());
        personaggio.setCosto(createPersonaggioRequest.getCosto());
        personaggio.setChiaveCampionato(createPersonaggioRequest.getChiaveCampionato());

        personaggio.setChiave(String.format("%s%s", createPersonaggioRequest.getChiaveCampionato(), createPersonaggioRequest.getNominativo()));
        personaggiService.savePersonaggio(personaggio);
        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Personaggio.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-personaggi/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<List<Personaggio>> readPersonaggi(@PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /read-personaggi/" + chiaveCampionato);

        return ResponseEntity.ok(personaggiService.readPersonaggi(chiaveCampionato));
    }

}
