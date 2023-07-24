package ampus.config;

import ampus.api.AppUserOAuth2Saver;
import ampus.db.reposirory.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserRepository userRepository) throws Exception {
                http
                .authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated()
                ).csrf(AbstractHttpConfigurer::disable)
                .oauth2Login(oauth2Login -> oauth2Login.successHandler(new AppUserOAuth2Saver(userRepository)));
        return http.build();
    }
}
