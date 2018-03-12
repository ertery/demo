package com.ut.demo.services

import com.ut.demo.dto.RecordDto
import com.ut.demo.dto.ResponseDto
import com.ut.demo.entity.RecordEntity
import com.ut.demo.enums.LoggingLevel
import com.ut.demo.repositories.RecordsRepository
import com.ut.demo.repositories.UserRepository
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.transaction.Transactional


@Service
open class RecordService(val recordsRepository: RecordsRepository, val userRepository: UserRepository) {

    private val sort = Sort(Sort.Order(Sort.Direction.ASC, "dt"))

    @Transactional
    open fun saveRecord(record: RecordDto): Long {
        val user = SecurityContextHolder.getContext().authentication.principal as User
        val dbUser = userRepository.findByUsername(username = user.username)
        val newRecord = RecordEntity(dt = LocalDateTime.parse(record.dt, DateTimeFormatter.ISO_DATE_TIME),
                message = record.message, level = LoggingLevel.valueOf(record.level), author = user.username)
        newRecord.user = dbUser
        val savedRecord = recordsRepository.saveAndFlush(newRecord)
        if (savedRecord?.id == null) {
            return -1
        }
        return savedRecord.id
    }

    @Transactional
    open fun getRecords(page: Int, size: Int): List<Any> {
        val paging: Pageable = PageRequest(page, size, sort)
        return recordsRepository.findAll(paging).map { content ->
            ResponseDto(
                    dt = content.dt.toString(),
                    author = content.author,
                    message = content.message,
                    level = content.level.toString())
        }
                .toList()
    }

    @Transactional
    open fun getAllRecords(): List<ResponseDto> {
        return recordsRepository.findAll(sort).map { content ->
            ResponseDto(
                    dt = content.dt.toString(),
                    author = content.author,
                    message = content.message,
                    level = content.level.toString())
        }
                .toList()

    }
}