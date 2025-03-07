package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Azione;
import it.customfanta.be.model.AzionePersonaggio;
import it.customfanta.be.model.Esito;
import it.customfanta.be.service.AzioniPersonaggiService;
import it.customfanta.be.service.AzioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(originPatterns = "*", allowCredentials = "true", allowedHeaders = "*")
public class AzioniController {

    private static final Logger logger = Logger.getLogger(AzioniController.class.getName());

    @Autowired
    private AzioniService azioniService;

    @Autowired
    private AzioniPersonaggiService azioniPersonaggiService;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Azione.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-all-azioni", produces = { "application/json" })
    public ResponseEntity<List<Azione>> readAzioni() {
        logger.info("RECEIVED GET /read-all-azioni");
        return ResponseEntity.ok(azioniService.realAll());
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-azione", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createAzione(@RequestBody Azione azione, @RequestHeader("profilo") String profilo) throws URISyntaxException {
        logger.info("RECEIVED POST /create-azione");
        if("ADMIN".equals(profilo)) {
            azioniService.saveAzione(azione);
            return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new Esito("KO"));
        }
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/add-azione-to-personaggio", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> addAzionePersonaggio(@RequestBody AzionePersonaggio azionePersonaggio, @RequestHeader("profilo") String profilo) throws URISyntaxException {
        logger.info("RECEIVED POST /add-azione-to-personaggio");
        if("ADMIN".equals(profilo)) {
            azionePersonaggio.setDataEsecuzione(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            azioniPersonaggiService.saveAzionePersonaggio(azionePersonaggio);
            return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
        } else {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).body(new Esito("KO"));
        }
    }

}
