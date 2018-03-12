package com.ut.demo.dto

data class ErrorResponse(val errors: List<ValidationError> = arrayListOf()) {

}