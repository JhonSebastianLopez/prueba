package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SeguridadWeb {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()  // Permite acceso a todo
            )
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("email")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio",true)
                .permitAll())
            .httpBasic().disable()       // Desactiva autenticación básica
            .csrf(csrf -> csrf.disable()); // Desactiva CSRF (solo en desarrollo)

        return http.build();
    }
}


// package com.egg.biblioteca;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;

// @Configuration
// @EnableWebSecurity
// public class SeguridadWeb {

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

//         httpSecurity
//             .authorizeHttpRequests(authorize -> authorize
//                 .requestMatchers("/admin/**").hasRole("ADMIN")
//                 .requestMatchers("/usuarios/**").authenticated()
//                 .requestMatchers("/css/", "/js/", "/img/", "/**").permitAll()
//                 .requestMatchers("/login","/registro","/registrar").permitAll()
//                 .anyRequest().authenticated())
//             .formLogin((form) -> form
//                 .loginPage("/login")
//                 .loginProcessingUrl("/logincheck")
//                 .usernameParameter("email")
//                 .passwordParameter("password")
//                 .defaultSuccessUrl("/inicio",true)
//                 .permitAll())
//             .logout((logout) -> logout
//                 .logoutUrl("/logout")
//                 .logoutSuccessUrl("/")
//                 .permitAll())
//             .csrf(csrf -> csrf
//                 .disable());

//         return httpSecurity.build();

//     }

//     @Bean
//     public BCryptPasswordEncoder passwordEncoder(){
//         return new BCryptPasswordEncoder();
//     }

// }
