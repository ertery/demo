package com.ut.demo.auth

import com.ut.demo.entity.UserEntity
import com.ut.demo.repositories.UserRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import java.util.Arrays.asList
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        val user: UserEntity = userRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return org.springframework.security.core.userdetails.User(username, user.password, getGrantedAuthorities(username))
    }

    private fun getGrantedAuthorities(username: String?): MutableCollection<GrantedAuthority> {
        var authorities: MutableCollection<GrantedAuthority> = mutableListOf()
        if (username.equals("admin") || username.equals("user")) {
            authorities = asList<GrantedAuthority>(SimpleGrantedAuthority("ROLE_ADMIN"), SimpleGrantedAuthority("ROLE_BASIC"))
        } else if (username.equals("other")) {
            authorities = asList<GrantedAuthority>(SimpleGrantedAuthority("ROLE_BASIC"))
        }
        return authorities
    }
}

