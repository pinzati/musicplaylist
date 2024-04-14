package hh.sof03.musicplaylist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

// h2-console import
// H2-CONSOLE ASETUKSIA EI SOVELLUKSEN TUOTANTOVERSIOON !!
import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(toH2Console()).permitAll() // h2-console
                        .requestMatchers(antMatcher("/register")).permitAll()
                        .requestMatchers(antMatcher("/playlistlist")).permitAll()
                        .requestMatchers(antMatcher("/showplaylist/{playlistid}")).permitAll()
                        .requestMatchers(antMatcher("/css/**")).permitAll()
                        .anyRequest().authenticated())
                .csrf(csrf -> csrf // h2-console changes start
                        .ignoringRequestMatchers(toH2Console()))
                .headers(headers -> headers
                        .frameOptions(frameoptions -> frameoptions
                                .disable())) // h2-console changes end
                .formLogin(formlogin -> formlogin
                        // spring security has a default login page so .loginPage can be deleted
                        // and login.html
                        .loginPage("/login")
                        .defaultSuccessUrl("/playlistlist", true)
                        .permitAll())
                .logout(logout -> logout
                        .permitAll());

        return http.build();
    }

    // use user entities
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
