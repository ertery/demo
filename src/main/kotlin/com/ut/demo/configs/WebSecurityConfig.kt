package com.ut.demo.configs

import com.ut.demo.auth.BasicAuthenticationPoint
import com.ut.demo.auth.CustomUserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import com.ut.demo.auth.CustomAccessDeniedHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
open class WebSecurityConfig(private val basicAuthenticationPoint: BasicAuthenticationPoint,
                             private val userDetailsService: CustomUserDetailsService,
                             private val customAccessDeniedHandler: CustomAccessDeniedHandler) : WebSecurityConfigurerAdapter() {


    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService)
    }


    override fun configure(http: HttpSecurity) {
        http
                .authorizeRequests()
                .antMatchers("/*").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
                .httpBasic().authenticationEntryPoint(basicAuthenticationPoint)
                .and()
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
    }
}