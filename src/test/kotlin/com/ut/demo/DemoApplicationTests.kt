package com.ut.demo

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import javax.servlet.Filter


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class DemoApplicationTests {


    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @Autowired
    private lateinit var springSecurityFilterChain: Filter


    @Before
    fun setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .addFilters<DefaultMockMvcBuilder>(springSecurityFilterChain)
                .build()
    }

    @Test
    fun testThatUserOtherNotHaveAccessToAPI() {
        mockMvc.perform(get("/demo/records")
                .with(httpBasic("other", "other")))
                .andExpect(status().is4xxClientError)
        mockMvc.perform(get("/demo/")
                .with(httpBasic("other", "other")))
                .andExpect(status().isForbidden)
    }

    @Test
    fun testThatUserAdminHasAccessToAPI() {
        mockMvc.perform(get("/demo/records")
                .with(httpBasic("admin", "admin")))
                .andExpect(status().isOk)
    }

    @Test
    fun testUnauthorizedWithoutUser() {
        mockMvc.perform(get("/demo/records"))
                .andExpect(status().isUnauthorized)
        mockMvc.perform(get("/demo/"))
                .andExpect(status().isUnauthorized)
    }

    @Test
    fun testUnauthorizedWithUser() {
        mockMvc.perform(get("/demo/records")
                .with(httpBasic("demo", "demo")))
                .andExpect(status().isUnauthorized)
        mockMvc.perform(get("/demo/")
                .with(httpBasic("demo", "demo")))
                .andExpect(status().isUnauthorized)
    }
}
