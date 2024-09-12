package com.csgp.arda;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.csgp.arda.service.UserDetailsServiceImplementation;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;
import java.util.Arrays;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsServiceImplementation userDetailsServiceImplementation;
    private final AuthenticationFilter authenticationFilter;
    private final AuthEntryPoint exceptionHandler;
   
    public SecurityConfig(UserDetailsServiceImplementation userDetailsServiceImplementation, 
    AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
    }

    // Para autenticar usuarios de la base de datos
    public void configureGlobal (AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImplementation).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

     
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // permito todos los orígenes, todos los métodos y todos los headers
        // ESTO TENGO QUE CAMBIARLO DESPUÉS ANTES DE HACER EL DEPLOY, UNA VEZ QUE TENGO EL FRONTEND 
        config.setAllowedOrigins(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(false);
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /* 
        http.csrf((csrf) -> csrf.disable()).cors(withDefaults())
        .authorizeHttpRequests((authorizeHttpRequests) ->
        authorizeHttpRequests.anyRequest().permitAll());

        */
        http.csrf((csrf) -> csrf.disable())
            .cors(withDefaults()) // agrego CORS
            .sessionManagement((sessionManagement) -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.requestMatchers(HttpMethod.POST, "/login").permitAll().anyRequest().authenticated())
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // agrego el filtro para autenticación en cada petición
            .exceptionHandling((exceptionHandling) -> 
                exceptionHandling.authenticationEntryPoint(exceptionHandler));
            
        return http.build();    
    
    }

    
   
}
