package com.ut.demo.dto

data class ErrorResponse(var errors: List<ValidationError> = arrayListOf()) {

}