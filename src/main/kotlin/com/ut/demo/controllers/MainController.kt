package com.ut.demo.controllers

import com.ut.demo.dto.ErrorResponse
import com.ut.demo.dto.LoggingDto
import com.ut.demo.dto.RecordDto
import com.ut.demo.dto.ValidationError
import com.ut.demo.services.RecordService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/demo")
@CrossOrigin(origins = ["*"])
class MainController(private val recordService: RecordService) {

    @GetMapping("")
    fun getTest(): String = "Приветики"

    @PostMapping("/record")
    fun addRecord(@Valid @RequestBody record: RecordDto, result: BindingResult): ResponseEntity<*> {
        if (result.hasErrors()) {
            val errors: ArrayList<ValidationError> = arrayListOf()
            result.fieldErrors.forEach { e -> run { errors.add(ValidationError(field = e.field, message = e.defaultMessage)) } }
            return ResponseEntity(ErrorResponse(errors = errors), HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity(LoggingDto(id = recordService.saveRecord(record)), HttpStatus.OK)
    }

    @GetMapping("/records")
    fun getRecords(@RequestParam(name = "page", required = false) page: Int?,
                   @RequestParam(name = "size", required = false) size: Int?): ResponseEntity<*> {
        if (page == null || size == null) {
            return ResponseEntity(recordService.getAllRecords(), HttpStatus.OK)
        }
        return ResponseEntity(recordService.getRecords(page, size), HttpStatus.OK)
    }
}