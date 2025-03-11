package it.customfanta.be.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "https://customfanta.github.io", allowCredentials = "true", allowedHeaders = "*")
public class DropTableController extends BaseController {

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
    private UsersService usersService;

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
    @RequestMapping(method = RequestMethod.GET, value = "/drop-users", produces = { "application/json" })
    public ResponseEntity<Esito> dropUsers() {
        usersService.dropUsers();
        return ResponseEntity.ok(new Esito("OK"));
    }

}
