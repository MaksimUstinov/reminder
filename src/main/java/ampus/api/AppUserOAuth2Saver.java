package ampus.api;

import ampus.db.entities.User;
import ampus.db.reposirory.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AppUserOAuth2Saver extends SavedRequestAwareAuthenticationSuccessHandler implements Consumer<OAuth2User> {


    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        super.onAuthenticationSuccess(request, response, authentication);
        if (authentication.getPrincipal() instanceof OAuth2User) {
            this.accept((OAuth2User) authentication.getPrincipal());
        }
    }

    @Override
    @Transactional
    public void accept(OAuth2User oAuth2User) {
        var login = (String)oAuth2User.getAttribute("login");
        if (userRepository.findByUserName(Objects.nonNull(login) ? login : oAuth2User.getName()).isEmpty()) {
            userRepository.save(User
                    .builder()
                    .userName(Objects.nonNull(login) ? login : oAuth2User.getName())
                    .email(oAuth2User.getAttribute("email"))
                    .telegram(oAuth2User.getAttribute("telegram"))
                    .build());
        }
    }
}
