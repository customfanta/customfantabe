package it.customfanta.be.controller;

import io.jsonwebtoken.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.customfanta.be.model.Esito;
import it.customfanta.be.model.User;
import it.customfanta.be.security.MD5Security;
import it.customfanta.be.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@CrossOrigin(origins = "https://customfanta.github.io", allowCredentials = "true", allowedHeaders = "*")
public class UsersController extends BaseController {

    private static final Logger logger = Logger.getLogger(UsersController.class.getName());

    @Autowired
    private UsersService usersService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/get-utente-loggato", produces = { "application/json" })
    public ResponseEntity<User> getUtenteLoggato() {
        logger.info("RECEIVED GET /get-utente-loggato");

        if(!userData.isLogged()) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }

        User user = new User();
        user.setUsername(userData.getUsername());
        user.setMail(userData.getMail());
        user.setNome(userData.getNome());
        user.setProfile(userData.getProfile());

        return ResponseEntity.ok(user);
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = User.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.POST, value = "/make-login", produces = { "application/json" }, consumes = { "application/json"})
    public ResponseEntity<User> makeLogin(@RequestBody User userLogin) {
        logger.info("RECEIVED POST /make-login");
        User user = usersService.findUser(userLogin);
        if(user != null) {
            if(MD5Security.getMD5Pass(userLogin.getPassword()).equals(user.getPassword())) {
                user.setPassword(null);

                String jwt = Jwts.builder().subject(user.getUsername()).claim("username", user.getUsername()).claim("nome", user.getNome()).claim("mail", user.getMail()).claim("profile", user.getProfile())
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

                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
            }
        } else {
            return ResponseEntity.notFound().build();
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
    public ResponseEntity<Esito> createUser(@RequestBody User user) throws URISyntaxException {
        logger.info("RECEIVED POST /create-user");
        if(usersService.findUser(user) != null) {
            return ResponseEntity.badRequest().build();
        }
        user.setProfile("BASIC");
        user.setPassword(MD5Security.getMD5Pass(user.getPassword()));
        usersService.saveUser(user);
        return ResponseEntity.created(new URI("db")).body(new Esito("OK"));
    }


    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = User.class)))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/read-all-user", produces = { "application/json" })
    public ResponseEntity<List<User>> readUsers() {
        logger.info("RECEIVED GET /read-all-user");
        if(!"ADMIN".equals(userData.getProfile())) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        return ResponseEntity.ok(usersService.findAll());
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/delete-user/{username}", produces = { "application/json" })
    public ResponseEntity<Esito> deleteUserById(@PathVariable("username") String username) {
        logger.info("RECEIVED GET /delete-user/" + username);
        if(!"ADMIN".equals(userData.getProfile())) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        usersService.deleteByID(username);
        return ResponseEntity.ok(new Esito("OK"));
    }

    @Operation(
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = Esito.class))
                    })
            }
    )
    @RequestMapping(method = RequestMethod.GET, value = "/make-user-admin/{username}", produces = { "application/json" })
    public ResponseEntity<Esito> makeUserAdmin(@PathVariable("username") String username) {
        logger.info("RECEIVED GET /make-user-admin/" + username);
        if(!"ADMIN".equals(userData.getProfile())) {
            return ResponseEntity.status(HttpStatusCode.valueOf(401)).build();
        }
        User user = usersService.findById(username);
        user.setProfile("ADMIN");
        usersService.saveUser(user);
        return ResponseEntity.ok(new Esito("OK"));
    }

}
