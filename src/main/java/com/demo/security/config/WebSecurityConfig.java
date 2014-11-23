package com.demo.security.config;

import com.demo.security.handler.LoginSuccessHandler;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by yamorn on 2014/11/22.
 */
@EnableWebSecurity(debug = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .passwordParameter("j_password")
                .usernameParameter("j_username")
                .loginPage("/login")
                .defaultSuccessUrl("/index")
                .failureUrl("/login?error")
                .successHandler(new LoginSuccessHandler()) //  must be in order
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout?logout")
                .permitAll()
                .and()
                .sessionManagement()
                .maximumSessions(1)
                .expiredUrl("/expire")
//                .maxSessionsPreventsLogin(true)
                .and();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(new CustomUserDetailService());
//        auth.authenticationProvider(daoAuthenticationProvider);
        auth.inMemoryAuthentication()
                .withUser("guest").password("123").roles("USER").and()
                .withUser("admin").password("root").roles("ADMIN", "USER");
    }

//    class CustomUserDetailService implements UserDetailsService {
//        @Override
//        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//            Collection<GrantedAuthority> collection = new ArrayList<>();
//            collection.add(new SimpleGrantedAuthority("ADMIN"));
//            return new User("root", "root", collection);
//        }
//    }


}
