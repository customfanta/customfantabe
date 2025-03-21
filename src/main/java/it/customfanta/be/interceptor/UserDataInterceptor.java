package it.customfanta.be.interceptor;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import it.customfanta.be.model.UserData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class UserDataInterceptor implements HandlerInterceptor {

    @Value("${testing}")
    private boolean testing;

    @Autowired
    private UserData userData;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            return true;
        }

        if(testing) {
            userData.setUsername("AntoTest");
            userData.setNome("AntoTestName");
            userData.setMail("AntoTestMail");
            userData.setMailCertificata(true);
            userData.setLogged(true);
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (int i = 0; i < cookies.length; i++) {
                    Cookie cookie = cookies[i];
                    if ("user-jwt".equals(cookie.getName())) {
                        String jweValue = cookie.getValue();
                        Claims payload = Jwts.parser().unsecured().build().parseUnsecuredClaims(jweValue).getPayload();
                        userData.setUsername(String.valueOf(payload.get("username")));
                        userData.setNome(String.valueOf(payload.get("nome")));
                        userData.setMail(String.valueOf(payload.get("mail")));
                        userData.setMailCertificata("SI".equals(String.valueOf(payload.get("mailCertificata"))));
                        userData.setLogged(true);
                        return true;
                    }
                }
            }
        }

        return true;
    }
}
