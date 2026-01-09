package br.com.arenamatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF para o JSF funcionar corretamente (ou configure o token do JSF)
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Libera recursos visuais (CSS, JS, Imagens do PrimeFaces)
                        .requestMatchers("/javax.faces.resource/**", "/jakarta.faces.resource/**").permitAll()
                        // Libera a página de Login e Cadastro de novos times
                        .requestMatchers(
                                "/", "/login.xhtml", "/cadastro.xhtml",
                                "/javax.faces.resource/**", "/webjars/**",
                                "/api/**"
                        ).permitAll()
                        // Todo o resto exige login
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.xhtml") // Sua página personalizada
                        .loginProcessingUrl("/processar-login")
                        .defaultSuccessUrl("/app/dashboard.xhtml", true)
                        .failureUrl("/login.xhtml?erro=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/sair")
                        //.logoutSuccessUrl("/app/dashboard.xhtml")
                        .logoutSuccessUrl("/login.xhtml")
                        );

        return http.build();
    }
}
