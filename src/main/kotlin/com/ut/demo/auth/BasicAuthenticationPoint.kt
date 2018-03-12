package com.ut.demo.auth

import com.ut.demo.dto.AuthError
import org.springframework.http.MediaType
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class BasicAuthenticationPoint : BasicAuthenticationEntryPoint(), Serializable {

    private val serialVersionUID = -8970718410437077606L

    override fun commence(request: HttpServletRequest?, response: HttpServletResponse, authEx: AuthenticationException) {
        val error = AuthError("Access denied")
        val jackson2JsonObjectMapper = Jackson2JsonObjectMapper()
        val json = jackson2JsonObjectMapper.toJson(error)
        response.addHeader("WWW-Authenticate", "Basic realm=$realmName")
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.toString()
        response.writer.write(json)
    }

    override fun afterPropertiesSet() {
        realmName = "Demo"
        super.afterPropertiesSet()
    }

}
