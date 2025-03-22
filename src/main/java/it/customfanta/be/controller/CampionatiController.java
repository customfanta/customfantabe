package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.model.request.CreaCampionatoRequest;
import it.customfanta.be.service.CampionatiService;
import it.customfanta.be.service.UtentiCampionatiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class CampionatiController extends BaseController {

    private static final Logger logger = Logger.getLogger(CampionatiController.class.getName());

    @Autowired
    private CampionatiService campionatiService;

    @Autowired
    private UtentiCampionatiService utentiCampionatiService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CampionatiUtenteResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/campionati-utente", produces = { "application/json" })
    public ResponseEntity<List<CampionatiUtenteResponse>> getCampionatiUtente() {
        logger.info("RECEIVED GET /campionati-utente");
        List<CampionatiUtenteResponse> response = new ArrayList<>();

        List<UtenteCampionato> utenteCampionati = utentiCampionatiService.findByUsernameUtente(userData.getUsername());
        for(UtenteCampionato utenteCampionato : utenteCampionati) {
            response.add(getCampionatiUtenteResponse(utenteCampionato, campionatiService.findByChiave(utenteCampionato.getChiaveCampionato())));
        }

        return ResponseEntity.ok(response);
    }

    private static CampionatiUtenteResponse getCampionatiUtenteResponse(UtenteCampionato utenteCampionato, Campionato campionato) {
        CampionatiUtenteResponse campionatiUtenteResponse = new CampionatiUtenteResponse();
        campionatiUtenteResponse.setChiaveCampionato(campionato.getChiave());
        campionatiUtenteResponse.setDescrizioneCampionato(campionato.getDescrizione());
        campionatiUtenteResponse.setOwnerCampionato(campionato.getUsernameUtenteOwner());
        campionatiUtenteResponse.setRuoloUtente(utenteCampionato.getRuoloUtente());
        campionatiUtenteResponse.setNomeCampionato(campionato.getNome());
        return campionatiUtenteResponse;
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CampionatiUtenteResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/utenti-campionato/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<List<UtenteCampionato>> getUtentiCampionato(@PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /campionati-utente/" + chiaveCampionato);

        return ResponseEntity.ok(utentiCampionatiService.findByChiaveCampionato(chiaveCampionato));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/crea-campionato", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createCampionato(@RequestBody CreaCampionatoRequest creaCampionatoRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /crea-campionato");

        String chiaveCampionato = String.format("%s%s%s", creaCampionatoRequest.getNome(), userData.getUsername(), UUID.randomUUID());

        Campionato campionato = new Campionato();

        campionato.setNome(creaCampionatoRequest.getNome());
        campionato.setDescrizione(creaCampionatoRequest.getDescrizione());
        campionato.setChiave(chiaveCampionato);
        campionato.setUsernameUtenteOwner(userData.getUsername());
        campionatiService.save(campionato);

        UtenteCampionato utenteCampionato = new UtenteCampionato();
        utenteCampionato.setChiave(String.format("%s%s", chiaveCampionato, userData.getUsername()));
        utenteCampionato.setChiaveCampionato(chiaveCampionato);
        utenteCampionato.setUsernameUtente(userData.getUsername());
        utenteCampionato.setRuoloUtente("OWNER");
        utentiCampionatiService.save(utenteCampionato);

        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }
    
}
