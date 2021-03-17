package com.lanc.planit.config;

import com.lanc.planit.filter.MyCorsFilter;
import com.lanc.planit.filter.SecurityConfigFilter;
import com.lanc.planit.model.MyUserRole;
import com.lanc.planit.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
/**
 * @Author Luigi Lin
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserService service;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .authorizeRequests()
                    .antMatchers("/plan").hasRole("USER")
                    .antMatchers("/task").hasRole("USER")
                    .antMatchers("/signup").permitAll()
                    .antMatchers("/h2").permitAll()
                    .antMatchers("/test").permitAll()
                    .antMatchers("/test/user").hasRole("USER").and()
                .httpBasic().and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .and()
                .addFilterBefore(new MyCorsFilter(), WebAsyncManagerIntegrationFilter.class)
                .addFilterAfter(new SecurityConfigFilter(), FilterSecurityInterceptor.class);
    }
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8084"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        //configuration.setExposedHeaders(Arrays.asList("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }

//    @Bean
//    @Override
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("group6")
//                .password("1234")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }


//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder());
//    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(service).passwordEncoder(bCryptPasswordEncoder());
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
