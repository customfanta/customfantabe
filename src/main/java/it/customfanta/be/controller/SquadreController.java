package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.model.request.SquadraRequest;
import it.customfanta.be.repository.PersonaggiRepository;
import it.customfanta.be.service.AzioniPersonaggiService;
import it.customfanta.be.service.AzioniService;
import it.customfanta.be.service.SquadrePersonaggiService;
import it.customfanta.be.service.SquadreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
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

    @Autowired
    private PersonaggiRepository personaggiRepository;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/crea-squadra", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createSquadra(@RequestBody CreaSquadraRequest creaSquadraRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /crea-squadra");

        SquadraRequest squadraRequest = creaSquadraRequest.getSquadra();
        Squadra squadra = new Squadra();
        squadra.setNome(squadraRequest.getNome());
        squadra.setDescrizione(squadraRequest.getDescrizione());
        squadra.setChiaveCampionato(squadraRequest.getChiaveCampionato());
        squadra.setChiave(String.format("%s%s%s", squadraRequest.getChiaveCampionato(), userData.getUsername(), squadraRequest.getNome()));
        squadra.setUsernameUtente(userData.getUsername());
        squadreService.saveSquadra(squadra);

        SquadraPersonaggio squadraPersonaggio = new SquadraPersonaggio();
        squadraPersonaggio.setChiaveSquadra(squadra.getChiave());
        for(String chiavePersonaggio : creaSquadraRequest.getChiaviPersonaggi()) {
            squadraPersonaggio.setChiavePersonaggio(chiavePersonaggio);
            squadraPersonaggio.setChiave(String.format("%s%s", squadra.getChiave(), chiavePersonaggio));
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
    @RequestMapping(method = RequestMethod.GET, value = "/read-squadra/{usernameUtente}/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<ReadSquadraResponse> readSquadra(@PathVariable("usernameUtente") String usernameUtente, @PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /read-squadra/" + usernameUtente + "/" + chiaveCampionato);

        ReadSquadraResponse readSquadraResponse = new ReadSquadraResponse();

        Squadra squadra = squadreService.readSquadraByUtente(usernameUtente, chiaveCampionato);
        if(squadra == null) {
            return ResponseEntity.ok(null);
        }

        readSquadraResponse.setSquadra(squadra);

        List<PersonaggioResponse> personaggiResponse = new ArrayList<>();

        List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByChiaveSquadra(squadra.getChiave());

        for(SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
            PersonaggioResponse personaggioResponse = new PersonaggioResponse();

            personaggioResponse.setNomePersonaggio(personaggiRepository.findById(squadraPersonaggio.getChiavePersonaggio()).get().getNominativo());

            List<AzionePersonaggio> azioniPersonaggi = azioniPersonaggiService.readByChiavePersonaggio(squadraPersonaggio.getChiavePersonaggio());
            int punteggioPersonaggio = 0;
            for(AzionePersonaggio azionePersonaggio : azioniPersonaggi) {
                Azione azione = azioniService.readByChiave(azionePersonaggio.getChiaveAzione());
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
    @RequestMapping(method = RequestMethod.GET, value = "/delete-squadra/{usernameUtente}/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<Esito> deleteSquadra(@PathVariable("usernameUtente") String usernameUtente, @PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /delete-squadra/" + usernameUtente + "/" + chiaveCampionato);

        Squadra squadra = squadreService.readSquadraByUtente(usernameUtente, chiaveCampionato);
        if(squadra == null) {
            return ResponseEntity.ok(new Esito("KO"));
        }

        List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByChiaveSquadra(squadra.getChiave());
        for(SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
            squadrePersonaggiService.deleteSquadraPersonaggioById(squadraPersonaggio.getChiave());
        }

        squadreService.deleteSquadraById(squadra.getChiave());
        return ResponseEntity.ok(new Esito("OK"));
    }

}
