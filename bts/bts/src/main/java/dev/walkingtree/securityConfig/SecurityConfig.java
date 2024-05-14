package dev.walkingtree.securityConfig;


import dev.walkingtree.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    private final UserRepository securityUserRepository;

    public SecurityConfig(UserRepository securityUserRepository) {
        this.securityUserRepository = securityUserRepository;
    }
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {

        // for InMemoryAuthentication
//        UserDetails user1 = User.builder()
//                .username("aman")
//                .password(passwordEncoder().encode("123"))
//                .roles("ADMIN")
//                .build();
//        UserDetails user2 = User.builder()
//                .username("harman")
//                .password(passwordEncoder().encode("harman"))
//                .roles("USER")
//                .build();
//
//        UserDetails user3 = User.builder()
//                .username("dev")
//                .password(passwordEncoder().encode("dev"))
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user1, user2);
        return username -> securityUserRepository
                .findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("invalid credentials"));
    }


    @Bean
    AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

        // set password encoder
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        // set UserDetailsService
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());

        return daoAuthenticationProvider;
    }
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)

                .cors(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/public/**", "/home", "/newLogin").permitAll()
                        .requestMatchers("/static/**", "/image/**","/contact", "/processcontact").permitAll()
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/signup/**").permitAll()
                        .anyRequest().authenticated())

                .formLogin(login -> login
                        .loginPage("/newLogin")
                        .defaultSuccessUrl("/")
                        .loginProcessingUrl("/processlogin")
                        .permitAll())
                .exceptionHandling(exception -> exception
                        .accessDeniedPage("/accessDenied"))

                .logout(Customizer.withDefaults())

                .build();
    }
}
