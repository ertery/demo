package com.ut.demo.auth

import com.ut.demo.dto.AuthError
import org.springframework.http.MediaType
import org.springframework.integration.support.json.Jackson2JsonObjectMapper
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class CustomAccessDeniedHandler : AccessDeniedHandler {

    override fun handle(request: HttpServletRequest?, response: HttpServletResponse, p2: AccessDeniedException?) {
        val error = AuthError("User other does not have access")
        val jackson2JsonObjectMapper = Jackson2JsonObjectMapper()
        val json = jackson2JsonObjectMapper.toJson(error)
        response.addHeader("WWW-Authenticate", "Basic realm=" + "Demo")
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.characterEncoding = StandardCharsets.UTF_8.toString()
        response.writer.write(json)
    }
}