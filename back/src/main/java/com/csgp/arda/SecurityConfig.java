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
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final CustomLogoutHandler customLogoutHandler;

    public SecurityConfig(UserDetailsServiceImplementation userDetailsServiceImplementation, 
    AuthenticationFilter authenticationFilter, AuthEntryPoint exceptionHandler, CustomLogoutHandler customLogoutHandler) {
        this.userDetailsServiceImplementation = userDetailsServiceImplementation;
        this.authenticationFilter = authenticationFilter;
        this.exceptionHandler = exceptionHandler;
        this.customLogoutHandler = customLogoutHandler;         
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
        config.setAllowedOrigins(Arrays.asList("https://congenial-barnacle-qg7qqr744qxf64gv-5173.app.github.dev",
        "https://congenial-barnacle-qg7qqr744qxf64gv-5174.app.github.dev"));
        config.setAllowedMethods(Arrays.asList("*"));
        config.setAllowedHeaders(Arrays.asList("*"));
        config.setAllowCredentials(true); // permito los cookies ya que guardo el token en HttpOnly
        config.applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        /*
        http.csrf((csrf) -> csrf.disable())
            .cors(withDefaults())
            .sessionManagement((sessionManagement) -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests.anyRequest().permitAll())
         */
        
        http.csrf((csrf) -> csrf.disable())
            .cors(withDefaults()) // agrego CORS
            .sessionManagement((sessionManagement) -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                    .requestMatchers(HttpMethod.GET, "/actuator/mappings").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users/credentials").permitAll()
                    .requestMatchers(HttpMethod.POST, "/users/login").permitAll()
                    .requestMatchers(HttpMethod.GET,"/posts/image/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/").permitAll()
                    .anyRequest()    
                    .authenticated())
            .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class) // agrego el filtro para autenticación en cada petición
            .exceptionHandling((exceptionHandling) -> 
                exceptionHandling.authenticationEntryPoint(exceptionHandler))
            .logout(l->l
                        .logoutUrl("/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()
                        ));;
            
        return http.build();    
    
    }

    
   
}
