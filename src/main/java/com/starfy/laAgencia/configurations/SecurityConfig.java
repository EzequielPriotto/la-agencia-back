package com.starfy.laAgencia.configurations;

import com.starfy.laAgencia.configurations.components.CustomAuthenticationProvider;
import com.starfy.laAgencia.configurations.components.JwtAuthenticationEntryPoint;
import com.starfy.laAgencia.configurations.components.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable()           // desactivo el csrf (Cross-site request forgery) https://es.wikipedia.org/wiki/Cross-site_request_forgery
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and()  // le seteo la repuesta que quiero que haga cuando no este autorizado
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // le desactivo la creacion de session desde el lado del back (porque el front se encarga de eso)
                .authorizeRequests() // se utiliza para decirle que rutas bloquear o permitir (y con que permisos acceder a esos endpoints)
                .antMatchers("/swagger-ui/**", "/swagger-resources/**", "/swagger-ui.html", "/api-docs/**", "/v2/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST,"/auth/**", "/develop/**", "/contacto/**").permitAll() // le digo que me "abra" para todos los endpoints /auth/**
                .antMatchers(HttpMethod.GET,"/bailarines/**","/maestros/**", "/idiomas/**", "/textos/**", "/categorias/**", "/roles/**").permitAll() // le digo que me "abra" para todos los endpoints /auth/**
                .anyRequest().authenticated(); // le digo que "bloquee" el resto de endpoints

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Agregamos el filtro personalizado de autenticación

    }


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Override
    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
