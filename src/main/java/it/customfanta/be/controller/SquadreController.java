package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.model.request.SquadraRequest;
import it.customfanta.be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "https://customfantabe.web.app", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
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
    private PersonaggiService personaggiService;

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

        Squadra squadra = squadreService.readSquadraByUtente(usernameUtente, chiaveCampionato);
        if(squadra == null) {
            return ResponseEntity.ok(null);
        }

        ReadSquadraResponse readSquadraResponse = new ReadSquadraResponse();

        readSquadraResponse.setLaMiaSquadra(true);
        readSquadraResponse.setSquadra(squadra);

        List<PersonaggioResponse> personaggiResponse = new ArrayList<>();

        List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByChiaveSquadra(squadra.getChiave());

        for(SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
            PersonaggioResponse personaggioResponse = new PersonaggioResponse();

            personaggioResponse.setNomePersonaggio(personaggiService.readByChiave(squadraPersonaggio.getChiavePersonaggio()).getNominativo());

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

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = ReadSquadraResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/recupera-squadre-campionato/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<List<ReadSquadraResponse>> recuperaSquadreCampionato(@PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /recupera-squadre-campionato/" + chiaveCampionato);

        List<Squadra> squadre = squadreService.findByChiaveCampionato(chiaveCampionato);

        List<ReadSquadraResponse> response = new ArrayList<>();
        for(Squadra squadra : squadre) {
            ReadSquadraResponse readSquadraResponse = new ReadSquadraResponse();

            if(userData.getUsername().equals(squadra.getUsernameUtente())) {
                readSquadraResponse.setLaMiaSquadra(true);
            }

            readSquadraResponse.setSquadra(squadra);

            List<PersonaggioResponse> personaggiResponse = new ArrayList<>();

            List<SquadraPersonaggio> squadraPersonaggi = squadrePersonaggiService.readByChiaveSquadra(squadra.getChiave());

            for (SquadraPersonaggio squadraPersonaggio : squadraPersonaggi) {
                PersonaggioResponse personaggioResponse = new PersonaggioResponse();

                personaggioResponse.setNomePersonaggio(personaggiService.readByChiave(squadraPersonaggio.getChiavePersonaggio()).getNominativo());

                List<AzionePersonaggio> azioniPersonaggi = azioniPersonaggiService.readByChiavePersonaggio(squadraPersonaggio.getChiavePersonaggio());
                int punteggioPersonaggio = 0;
                for (AzionePersonaggio azionePersonaggio : azioniPersonaggi) {
                    Azione azione = azioniService.readByChiave(azionePersonaggio.getChiaveAzione());
                    if (azione != null) {
                        punteggioPersonaggio += azione.getPunteggio();
                    }
                }

                personaggioResponse.setPunteggioAttuale(punteggioPersonaggio);
                personaggiResponse.add(personaggioResponse);
            }

            readSquadraResponse.setPersonaggi(personaggiResponse);
            readSquadraResponse.setPunteggioSquadra(personaggiResponse.stream().mapToInt(PersonaggioResponse::getPunteggioAttuale).sum());

            response.add(readSquadraResponse);
        }

        response.sort((r1, r2) -> r2.getPunteggioSquadra().compareTo(r1.getPunteggioSquadra()));
        for(int i = 1; i < response.size()+1; i++) {
            response.get(i-1).setPosizioneClassifica(i);
        }

        return ResponseEntity.ok(response);
    }

}
