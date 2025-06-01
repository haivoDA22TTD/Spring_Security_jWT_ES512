package spring.boot.rsa512.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    
    private final AuthFilter authFilter;

  @Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
       .authorizeHttpRequests(auth -> auth
    .requestMatchers("/api/auth/**").permitAll()
      // Cho ADMIN thao tác tạo, sửa, xóa
            .requestMatchers(HttpMethod.POST, "/api/characters/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/api/characters/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.POST,"/api/cultivationmethods/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE,"/api/cultivationmethods/**").hasRole("ADMIN")
            // Cho cả USER và ADMIN xem
            .requestMatchers(HttpMethod.GET, "/api/characters/**").hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.GET,"/api/cultivationmethods/**").hasAnyRole("ADMIN", "USER")
    .anyRequest().authenticated()
)
.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);


    return http.build();
}


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
