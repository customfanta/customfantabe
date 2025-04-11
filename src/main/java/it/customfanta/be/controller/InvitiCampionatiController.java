package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.model.request.InvitaUtenteRequest;
import it.customfanta.be.repository.InvitiCampionatiRepository;
import it.customfanta.be.repository.UtentiCampionatiRepository;
import it.customfanta.be.service.CampionatiService;
import it.customfanta.be.service.UtentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class InvitiCampionatiController extends BaseController {

    private static final Logger logger = Logger.getLogger(InvitiCampionatiController.class.getName());

    @Autowired
    private InvitiCampionatiRepository invitiCampionatiRepository;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private CampionatiService campionatiService;

    @Autowired
    private UtentiCampionatiRepository utentiCampionatiRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/invita-utente", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> invitaUtente(@RequestBody InvitaUtenteRequest invitaUtenteRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /invita-utente");


        Utente utenteInvitato = utentiService.recuperaUtente(invitaUtenteRequest.getUsernameUtenteInvitato());

        if(utenteInvitato != null) {
            InvitoCampionato invitoCampionato = new InvitoCampionato();

            invitoCampionato.setUsernameUtenteInvitato(invitaUtenteRequest.getUsernameUtenteInvitato());
            invitoCampionato.setRuoloInvito(invitaUtenteRequest.getRuoloInvito());
            invitoCampionato.setChiaveCampionato(invitaUtenteRequest.getChiaveCampionato());
            invitoCampionato.setUsernameUtenteCheHaInvitato(userData.getUsername());
            invitoCampionato.setChiave(String.format("%s%s%s%s", userData.getUsername(), invitaUtenteRequest.getUsernameUtenteInvitato(), invitaUtenteRequest.getChiaveCampionato(), invitaUtenteRequest.getRuoloInvito()));
            invitiCampionatiRepository.save(invitoCampionato);

            simpMessagingTemplate.convertAndSend("/topic/nuovo-invito-ricevuto/" + invitoCampionato.getUsernameUtenteInvitato(), "NUOVO INVITO RICEVUTO");
            return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
        } else {
            return ResponseEntity.ok(new Esito("KO"));
        }
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InvitoCampionatoResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-inviti-ricevuti", produces = { "application/json" })
    public ResponseEntity<List<InvitoCampionatoResponse>> readInvitiRicevuti() {
        logger.info("RECEIVED GET /read-inviti-ricevuti");

        List<InvitoCampionato> findMyInvitiRicevuti = invitiCampionatiRepository.findByUsernameUtenteInvitato(userData.getUsername());

        List<InvitoCampionatoResponse> response = new ArrayList<>();

        for(InvitoCampionato invitoCampionato : findMyInvitiRicevuti) {
            InvitoCampionatoResponse invitoCampionatoResponse = new InvitoCampionatoResponse();

            invitoCampionatoResponse.setChiave(invitoCampionato.getChiave());
            invitoCampionatoResponse.setCampionato(campionatiService.findByChiave(invitoCampionato.getChiaveCampionato()));
            invitoCampionatoResponse.setUsernameUtenteInvitato(invitoCampionato.getUsernameUtenteInvitato());
            invitoCampionatoResponse.setUsernameUtenteCheHaInvitato(invitoCampionato.getUsernameUtenteCheHaInvitato());
            invitoCampionatoResponse.setRuoloInvito(invitoCampionato.getRuoloInvito());

            response.add(invitoCampionatoResponse);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InvitoCampionatoResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-inviti-invitati", produces = { "application/json" })
    public ResponseEntity<List<InvitoCampionatoResponse>> readInvitiInviati() {
        logger.info("RECEIVED GET /read-inviti-invitati");

        List<InvitoCampionato> findMyInvitiInviati = invitiCampionatiRepository.findByUsernameUtenteCheHaInvitato(userData.getUsername());

        List<InvitoCampionatoResponse> response = new ArrayList<>();

        for(InvitoCampionato invitoCampionato : findMyInvitiInviati) {
            InvitoCampionatoResponse invitoCampionatoResponse = new InvitoCampionatoResponse();

            invitoCampionatoResponse.setChiave(invitoCampionato.getChiave());
            invitoCampionatoResponse.setCampionato(campionatiService.findByChiave(invitoCampionato.getChiaveCampionato()));
            invitoCampionatoResponse.setUsernameUtenteInvitato(invitoCampionato.getUsernameUtenteInvitato());
            invitoCampionatoResponse.setUsernameUtenteCheHaInvitato(invitoCampionato.getUsernameUtenteCheHaInvitato());
            invitoCampionatoResponse.setRuoloInvito(invitoCampionato.getRuoloInvito());

            response.add(invitoCampionatoResponse);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = InvitoCampionatoResponse.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-inviti-campionato/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<List<InvitoCampionatoResponse>> readInvitiCampionato(@PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /read-inviti-campionato/" + chiaveCampionato);

        List<InvitoCampionato> findMyInvitiInviati = invitiCampionatiRepository.findByChiaveCampionato(chiaveCampionato);

        List<InvitoCampionatoResponse> response = new ArrayList<>();

        for(InvitoCampionato invitoCampionato : findMyInvitiInviati) {
            InvitoCampionatoResponse invitoCampionatoResponse = new InvitoCampionatoResponse();

            invitoCampionatoResponse.setChiave(invitoCampionato.getChiave());
//            invitoCampionatoResponse.setCampionato(campionatiRepository.findById(invitoCampionato.getChiaveCampionato()).get());
            invitoCampionatoResponse.setUsernameUtenteInvitato(invitoCampionato.getUsernameUtenteInvitato());
            invitoCampionatoResponse.setUsernameUtenteCheHaInvitato(invitoCampionato.getUsernameUtenteCheHaInvitato());
            invitoCampionatoResponse.setRuoloInvito(invitoCampionato.getRuoloInvito());

            response.add(invitoCampionatoResponse);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/accetta-invito/{chiaveInvito}", produces = { "application/json" })
    public ResponseEntity<Esito> accettaInvito(@PathVariable("chiaveInvito") String chiaveInvito) {
        logger.info("RECEIVED GET /accetta-invito" + chiaveInvito);

        InvitoCampionato invito = invitiCampionatiRepository.findById(chiaveInvito).get();

        UtenteCampionato utenteCampionato = new UtenteCampionato();
        utenteCampionato.setChiave(String.format("%s%s", invito.getChiaveCampionato(), invito.getUsernameUtenteInvitato()));
        utenteCampionato.setChiaveCampionato(invito.getChiaveCampionato());
        utenteCampionato.setUsernameUtente(invito.getUsernameUtenteInvitato());
        utenteCampionato.setRuoloUtente(invito.getRuoloInvito());
        utentiCampionatiRepository.save(utenteCampionato);

        invitiCampionatiRepository.deleteById(chiaveInvito);

        simpMessagingTemplate.convertAndSend("/topic/nuovo-utente-in-campionato/" + invito.getChiaveCampionato(), "NUOVO UTENTE IN CAMPIONATO");

        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/rifiuta-invito/{chiaveInvito}", produces = { "application/json" })
    public ResponseEntity<Esito> rifiutaInvito(@PathVariable("chiaveInvito") String chiaveInvito) {
        logger.info("RECEIVED GET /rifiuta-invito" + chiaveInvito);
        invitiCampionatiRepository.deleteById(chiaveInvito);
        return ResponseEntity.ok(new Esito("OK"));
    }

}
