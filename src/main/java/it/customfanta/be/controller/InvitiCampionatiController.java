package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.*;
import it.customfanta.be.repository.CampionatiRepository;
import it.customfanta.be.repository.InvitiCampionatiRepository;
import it.customfanta.be.repository.UtentiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class InvitiCampionatiController extends BaseController {

    private static final Logger logger = Logger.getLogger(InvitiCampionatiController.class.getName());

    @Autowired
    private InvitiCampionatiRepository invitiCampionatiRepository;

    @Autowired
    private UtentiRepository utentiRepository;

    @Autowired
    private CampionatiRepository campionatiRepository;

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
    public ResponseEntity<Esito> invitaUtente(@RequestBody InvitoCampionato invitoCampionato) throws URISyntaxException {
        logger.info("RECEIVED POST /invita-utente");

        Optional<Utente> utenteInvitato = utentiRepository.findByUsername(invitoCampionato.getUsernameUtenteInvitato());

        if(utenteInvitato.isPresent()) {
            invitoCampionato.setUsernameUtenteCheHaInvitato(userData.getUsername());
            invitoCampionato.setChiave(String.format("%s%s%s%s", userData.getUsername(), invitoCampionato.getUsernameUtenteInvitato(), invitoCampionato.getChiaveCampionato(), invitoCampionato.getRuoloInvito()));
            invitiCampionatiRepository.save(invitoCampionato);

            simpMessagingTemplate.convertAndSend("/topic/nuovo-invito-ricevuto/" + invitoCampionato.getUsernameUtenteInvitato(), "NUOVO INVITO RICEVUTO");
            return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
        } else {
            return ResponseEntity.notFound().build();
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
            invitoCampionatoResponse.setCampionato(campionatiRepository.findById(invitoCampionato.getChiaveCampionato()).get());
            invitoCampionatoResponse.setUsernameUtenteCheHaInvitato(invitoCampionato.getUsernameUtenteCheHaInvitato());
            invitoCampionatoResponse.setRuoloInvito(invitoCampionato.getRuoloInvito());

            response.add(invitoCampionatoResponse);
        }

        return ResponseEntity.ok(response);
    }

}
