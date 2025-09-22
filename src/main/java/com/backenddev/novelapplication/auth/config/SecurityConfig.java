package com.backenddev.novelapplication.auth.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    // SECURITY FILTER CHAIN ;
    // this is series of filters that handle security related task for incoming http request,
    // this processes request to enforce authentication, authorization and other security features.

    //CSRF (cross site request forgery);
    //an attack where a hacker tricks your browser into doing something on a website you are logged into, without you knowing.

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // this method configures the security rules for http requests,specifiying how users are authenticated.
       http
               .csrf(customizer -> customizer.disable())
               .authorizeHttpRequests(requests -> requests
                       .requestMatchers("/api/v1/auth/register", "/api/v1/auth/login")
                       .permitAll()
                       .anyRequest().authenticated())
               .formLogin(Customizer.withDefaults())
               .httpBasic(Customizer.withDefaults())
               .sessionManagement(session
                       -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                ;

       return http.build();
    }

    // this method sets the UserName, Password, and roles to the available ones in the database.
    // basically connects to the database to get a user and their features.
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)throws Exception {
        return config.getAuthenticationManager();
    }
}
