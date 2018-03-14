package com.ut.demo.controllers

import com.google.gson.Gson
import com.ut.demo.dto.RecordDto
import com.ut.demo.dto.ResponseDto
import com.ut.demo.enums.LoggingLevel
import com.ut.demo.services.RecordService
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = [org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration::class])
class MainControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var recordService: RecordService

    @Before
    fun setUp() {

    }

    @Test
    fun addRecordWithValidationError() {
        val dto = RecordDto("", "", "")
        val gson = Gson()
        val json = gson.toJson(dto)
        given(recordService.saveRecord(any())).willReturn(2L)
        this.mockMvc.perform(post("/demo/record")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
                .content(json)
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isBadRequest)
    }


    @Test
    fun addRecordSuccess() {
        val dto = RecordDto("1990-05-16T19:20:30+01:00", "ERROR", "Test")
        val gson = Gson()
        val json = gson.toJson(dto)
        given(recordService.saveRecord(any())).willReturn(1L)
        this.mockMvc.perform(post("/demo/record")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
                .content(json)
                .contentType("application/json;charset=UTF-8")
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk).andReturn()
    }


    @Test
    fun getRecords() {
        given(recordService.getRecords(0, 0)).willReturn(listOf(ResponseDto(dt = "2015-10-14T19:20:30+01:00",
                message = "demo", author = "admin", level = LoggingLevel.INFO.toString())))
        mockMvc
                .perform(get("/demo/records")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin"))
                        .accept(MediaType.parseMediaType("application/json;charset=UTF-8")))
                .andExpect(status().isOk)
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

}