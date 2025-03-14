package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.service.AzioniPersonaggiService;
import it.customfanta.be.service.AzioniService;
import it.customfanta.be.service.SquadrePersonaggiService;
import it.customfanta.be.service.SquadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "https://customfanta.github.io", allowCredentials = "true", allowedHeaders = "*")
public class SquadreController extends BaseController {

    private static final Logger logger = Logger.getLogger(SquadreController.class.getName());

    @Autowired
    private SquadreService squadreService;

    @Autowired
    private SquadrePersonaggiService squadrePersonaggiService;

    @Autowired
    private AzioniPersonaggiService azioniPersonaggiService;

    @Autowired
    private AzioniService azioniService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-squadra/{usernameUser}", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createSquadra(@RequestBody CreaSquadraRequest creaSquadraRequest, @PathVariable("usernameUser") String usernameUser) throws URISyntaxException {
        logger.info("RECEIVED POST /create-squadra/" +  usernameUser);

        if(!userData.getUsername().equals(usernameUser)) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new Esito("KO"));
        }

        Squadra squadra = creaSquadraRequest.getSquadra();
        squadra.setUsernameUser(usernameUser);
        squadra.setChiave(String.format("%s%s", usernameUser, squadra.getNome()));
        squadreService.saveSquadra(squadra);

        SquadraPersonaggio squadraPersonaggio = new SquadraPersonaggio();
        squadraPersonaggio.setNomeUtente(usernameUser);
        squadraPersonaggio.setNomeSquadra(squadra.getNome());
        for(String nomePersonaggio : creaSquadraRequest.getNomiPersonaggi()) {
            squadraPersonaggio.setNominativoPersonaggio(nomePersonaggio);
            squadraPersonaggio.setChiave(String.format("%s%s%s", usernameUser, squadra.getNome(), nomePersonaggio));
            squadrePersonaggiService.saveSquadraPersonaggio(squadraPersonaggio);
        }

        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ReadSquadraResponse.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-squadra/{usernameUser}", produces = { "application/json" })
    public ResponseEntity<ReadSquadraResponse> readSquadra(@PathVariable("usernameUser") String usernameUser) {
        logger.info("RECEIVED GET /read-squadra/" + usernameUser);

        ReadSquadraResponse readSquadraResponse = new ReadSquadraResponse();

        Squadra squadra = squadreService.readSquadraByUtente(usernameUser);
        if(squadra == null) {
            return ResponseEntity.notFound().build();
        }

        readSquadraResponse.setSquadra(squadra);

        List<PersonaggioResponse> personaggiResponse = new ArrayList<>();

        List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByNomeSquadra(squadra.getNome());

        for(SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
            PersonaggioResponse personaggioResponse = new PersonaggioResponse();
            personaggioResponse.setNomePersonaggio(squadraPersonaggio.getNominativoPersonaggio());

            List<AzionePersonaggio> azioniPersonaggi = azioniPersonaggiService.readByNomePersonaggio(squadraPersonaggio.getNominativoPersonaggio());
            int punteggioPersonaggio = 0;
            for(AzionePersonaggio azionePersonaggio : azioniPersonaggi) {
                Azione azione = azioniService.readByName(azionePersonaggio.getAzione());
                if(azione != null) {
                    punteggioPersonaggio += azione.getPunteggio();
                }
            }

            personaggioResponse.setPunteggioAttuale(punteggioPersonaggio);
            personaggiResponse.add(personaggioResponse);
        }

        readSquadraResponse.setPersonaggi(personaggiResponse);
        readSquadraResponse.setPunteggioSquadra(personaggiResponse.stream().mapToInt(PersonaggioResponse::getPunteggioAttuale).sum());

        return ResponseEntity.ok(readSquadraResponse);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/delete-squadra/{usernameUser}", produces = { "application/json" })
    public ResponseEntity<Esito> deleteSquadra(@PathVariable("usernameUser") String usernameUser) {
        logger.info("RECEIVED GET /delete-squadra/" + usernameUser);

        Squadra squadra = squadreService.readSquadraByUtente(usernameUser);
        if(squadra == null) {
            return ResponseEntity.notFound().build();
        }

        List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByNomeSquadra(squadra.getNome());
        for(SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
            squadrePersonaggiService.deleteSquadraPersonaggioById(squadraPersonaggio.getChiave());
        }

        squadreService.deleteSquadraById(squadra.getChiave());
        return ResponseEntity.ok(new Esito("OK"));
    }

}
