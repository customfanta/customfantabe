package it.customfanta.be.controller;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Azione;
import it.customfanta.be.model.AzionePersonaggio;
import it.customfanta.be.model.Esito;
import it.customfanta.be.model.request.AddAzionePersonaggioRequest;
import it.customfanta.be.model.request.CreateAzioneRequest;
import it.customfanta.be.service.AzioniPersonaggiService;
import it.customfanta.be.service.AzioniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io", "http://localhost:8080"}, allowCredentials = "true", allowedHeaders = "*")
public class AzioniController extends BaseController {

    private static final Logger logger = Logger.getLogger(AzioniController.class.getName());

    @Autowired
    private AzioniService azioniService;

    @Autowired
    private AzioniPersonaggiService azioniPersonaggiService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Azione.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-all-azioni/{chiaveCampionato}", produces = { "application/json" })
    public ResponseEntity<List<Azione>> readAzioni(@PathVariable("chiaveCampionato") String chiaveCampionato) {
        logger.info("RECEIVED GET /read-all-azioni/" + chiaveCampionato);
        return ResponseEntity.ok(azioniService.realAll(chiaveCampionato));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-azione", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createAzione(@RequestBody CreateAzioneRequest createAzioneRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /create-azione");
        Azione azione = new Azione();
        azione.setChiave(String.format("%s%s", createAzioneRequest.getChiaveCampionato(), createAzioneRequest.getAzione()));

        azione.setAzione(createAzioneRequest.getAzione());
        azione.setPunteggio(createAzioneRequest.getPunteggio());
        azione.setChiaveCampionato(createAzioneRequest.getChiaveCampionato());
        azione.setDescrizione(createAzioneRequest.getDescrizione());

        azioniService.saveAzione(azione);
        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/csv/upload-azioni", produces = { "application/json" }, consumes = { "multipart/form-data"})
    public ResponseEntity<Esito> uploadAzioniFromCsv(@RequestParam("file") MultipartFile file) throws URISyntaxException {
        logger.info("RECEIVED POST /upload-azioni");

        List<Azione> azioni = new ArrayList<>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                Azione azione = new Azione();

                azione.setAzione(record[0]);
                azione.setDescrizione(record[1]);
                azione.setPunteggio(Integer.valueOf(record[2]));
                azione.setChiaveCampionato(record[3]);

                azione.setChiave(String.format("%s%s", azione.getChiaveCampionato(), azione.getAzione()));

                azioni.add(azione);
            }
        } catch (IOException | CsvException ignored) {
        }

        if(reader != null) {
            try {
                reader.close();
            } catch (IOException ignored) {
            }
        }

        azioniService.saveAllAzione(azioni);

        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/add-azione-to-personaggio", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> addAzionePersonaggio(@RequestBody AddAzionePersonaggioRequest addAzionePersonaggioRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /add-azione-to-personaggio");
        AzionePersonaggio azionePersonaggio = new AzionePersonaggio();
        String dataEsecuzione = LocalDateTime.now().atZone(ZoneId.of("Europe/Rome")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss XXX z"));
        azionePersonaggio.setDataEsecuzione(dataEsecuzione);
        azionePersonaggio.setChiave(String.format("%s%s%s", addAzionePersonaggioRequest.getChiavePersonaggio(), addAzionePersonaggioRequest.getChiaveAzione(), dataEsecuzione));
        azionePersonaggio.setChiaveAzione(addAzionePersonaggioRequest.getChiaveAzione());
        azionePersonaggio.setChiavePersonaggio(addAzionePersonaggioRequest.getChiavePersonaggio());
        azioniPersonaggiService.saveAzionePersonaggio(azionePersonaggio);

        simpMessagingTemplate.convertAndSend("/topic/azione-personaggio-aggiunta", "AGGIORNATA AZIONE PERSONAGGIO");

        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/test-web-socket", produces = { "application/json"})
    public ResponseEntity<Esito> testWebSocket() {
        simpMessagingTemplate.convertAndSend("/topic/test-ws", "OK");
        return ResponseEntity.ok(new Esito("OK"));
    }
}
