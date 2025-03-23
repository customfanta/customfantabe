package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.service.*;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class DropTableController extends BaseController {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    private AzioniPersonaggiService azioniPersonaggiService;

    @Autowired
    private AzioniService azioniService;

    @Autowired
    private PersonaggiService personaggiService;

    @Autowired
    private SquadrePersonaggiService squadrePersonaggiService;

    @Autowired
    private SquadreService squadreService;

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private CampionatiService campionatiService;

    @Autowired
    private UtentiCampionatiService utentiCampionatiService;

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-azioni-personaggi", produces = { "application/json" })
    public ResponseEntity<Esito> dropAzioniPersonaggi() {
        azioniPersonaggiService.dropAzioniPersonaggi();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-azioni", produces = { "application/json" })
    public ResponseEntity<Esito> dropAzioni() {
        azioniService.dropAzioniService();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-personaggi", produces = { "application/json" })
    public ResponseEntity<Esito> dropPersonaggi() {
        personaggiService.dropPersonaggi();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-squadre-personaggi", produces = { "application/json" })
    public ResponseEntity<Esito> dropSquadrePersonaggi() {
        squadrePersonaggiService.dropSquadrePersonaggi();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-squadre", produces = { "application/json" })
    public ResponseEntity<Esito> dropSquadre() {
        squadreService.dropSquadre();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-utenti", produces = { "application/json" })
    public ResponseEntity<Esito> dropUtenti() {
        utentiService.dropTable();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-campionati", produces = { "application/json" })
    public ResponseEntity<Esito> dropCampionati() {
        campionatiService.dropTable();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-utenti-campionati", produces = { "application/json" })
    public ResponseEntity<Esito> dropUtentiCampionati() {
        utentiCampionatiService.dropTable();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-inviti-campionati", produces = { "application/json" })
    public ResponseEntity<Esito> dropInvitiCampionati() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS inviti_campionato")
                .executeUpdate();
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(responses = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))})})
    @RequestMapping(method = RequestMethod.GET, value = "/drop-configurazioni-campionati", produces = { "application/json" })
    public ResponseEntity<Esito> dropConfigurazioniCampionati() {
        entityManager.createNativeQuery("DROP TABLE IF EXISTS configurazioni_campionati")
                .executeUpdate();
        return ResponseEntity.ok(new Esito("OK"));
    }

}
