package it.customfanta.be.controller;

import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.model.Utente;
import it.customfanta.be.model.request.CreateUserRequest;
import it.customfanta.be.model.request.MakeLoginRequest;
import it.customfanta.be.security.MD5Security;
import it.customfanta.be.service.MailService;
import it.customfanta.be.service.UtentiService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = {"https://customfanta.github.io"}, allowCredentials = "true", allowedHeaders = "*")
public class UtentiController extends BaseController {

    private static final Logger logger = Logger.getLogger(UtentiController.class.getName());

    @Autowired
    private UtentiService utentiService;

    @Autowired
    private MailService mailService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Utente.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/get-utente-loggato", produces = { "application/json" })
    public ResponseEntity<Utente> getUtenteLoggato() {
        logger.info("RECEIVED GET /get-utente-loggato");

        if(!userData.isLogged()) {
            return ResponseEntity.ok(null);
        }

        Utente utente = new Utente();
        utente.setUsername(userData.getUsername());
        utente.setMail(userData.getMail());
        utente.setMailCertificata(userData.isMailCertificata());
        utente.setNome(userData.getNome());

        return ResponseEntity.ok(utente);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Utente.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/make-login", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Utente> makeLogin(@RequestBody MakeLoginRequest utenteLogin) {
        logger.info("RECEIVED POST /make-login");

        Utente search = new Utente();
        search.setMail(utenteLogin.getUsernameMail());
        search.setUsername(utenteLogin.getUsernameMail());

        Utente utente = utentiService.findUtente(search);
        if(utente != null) {
            if(MD5Security.getMD5Pass(utenteLogin.getPassword()).equals(utente.getPassword())) {
                utente.setPassword(null);

                String jwt = Jwts.builder().subject(utente.getUsername()).claim("username", utente.getUsername()).claim("nome", utente.getNome()).claim("mail", utente.getMail()).claim("mailCertificata", utente.getMailCertificata() ? "SI" : "NO")
                        .issuedAt(new Date())
                        .expiration(new Date(System.currentTimeMillis() + 14400000))
                        .compact();

                ResponseCookie responseCookie = ResponseCookie.from("user-jwt", jwt)
                        .maxAge(Duration.ofHours(4))
                        .path("/")
                        .secure(true)
                        .httpOnly(false)
                        .sameSite("None")
                        .build();

                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

                return ResponseEntity.ok(utente);
            } else {
                return ResponseEntity.ok(null);
            }
        } else {
            return ResponseEntity.ok(null);
        }
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/logout", produces = { "application/json" })
    public ResponseEntity<Esito> logOut() {
        logger.info("RECEIVED GET /logout");

        ResponseCookie responseCookie = ResponseCookie.from("user-jwt", "")
                .maxAge(0)
                .path("/")
                .secure(true)
                .httpOnly(false)
                .sameSite("None")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "201", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/create-user", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<Esito> createUser(@RequestBody CreateUserRequest createUserRequest) throws URISyntaxException {
        logger.info("RECEIVED POST /create-user");

        Utente utente = new Utente();
        utente.setUsername(createUserRequest.getUsername());
        utente.setMail(createUserRequest.getMail());
        if(utentiService.findUtente(utente) != null) {
            return ResponseEntity.badRequest().build();
        }
        utente.setPassword(MD5Security.getMD5Pass(createUserRequest.getPassword()));

        utente.setMailCertificata(false);

        String uuidMailCertificazione = UUID.randomUUID().toString();
        utente.setUuidMailCertificazione(uuidMailCertificazione);

        utentiService.saveUtente(utente);

        mailService.sendMail(utente.getMail(), "FantaCustom - Certifica la Mail", "Clicca il seguente link per certificare la tua mail:\nhttps://customfantabe.onrender.com/certifica-mail/"+uuidMailCertificazione);

        String jwt = Jwts.builder().subject(utente.getUsername()).claim("username", utente.getUsername()).claim("nome", utente.getNome()).claim("mail", utente.getMail()).claim("mailCertificata", utente.getMailCertificata() ? "SI" : "NO")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 14400000))
                .compact();

        ResponseCookie responseCookie = ResponseCookie.from("user-jwt", jwt)
                .maxAge(Duration.ofHours(4))
                .path("/")
                .secure(true)
                .httpOnly(false)
                .sameSite("None")
                .build();

        httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/certifica-mail/{uuidMailCertificazione}", produces = { "application/json" })
    public RedirectView certificaMail(@PathVariable("uuidMailCertificazione") String uuidMailCertificazione) {
        logger.info("RECEIVED GET /certifica-mail/"+uuidMailCertificazione);

        Utente utente = utentiService.findUtenteByUUIDMail(uuidMailCertificazione);
        if(utente != null) {
            utente.setUuidMailCertificazione(null);
            utente.setMailCertificata(true);
            utentiService.saveUtente(utente);
        } else {
            return new RedirectView("https://customfanta.github.io/certifica-mail-ko.html");
        }

        return new RedirectView("https://customfanta.github.io/certifica-mail-ok.html");
    }

}
