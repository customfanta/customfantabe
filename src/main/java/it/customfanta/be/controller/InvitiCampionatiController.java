package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.model.InvitoCampionato;
import it.customfanta.be.repository.InvitiCampionatiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class InvitiCampionatiController extends BaseController {

    private static final Logger logger = Logger.getLogger(InvitiCampionatiController.class.getName());

    @Autowired
    private InvitiCampionatiRepository invitiCampionatiRepository;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/invita-utente", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> invitaUtente(@RequestBody InvitoCampionato invitoCampionato) throws URISyntaxException {
        logger.info("RECEIVED POST /invita-utente");

        invitoCampionato.setUsernameUtenteCheHaInvitato(userData.getUsername());
        invitiCampionatiRepository.save(invitoCampionato);
        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

}
