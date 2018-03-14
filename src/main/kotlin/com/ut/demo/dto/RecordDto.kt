package com.ut.demo.dto

import com.ut.demo.validation.DateValidation
import com.ut.demo.validation.EnumValidation


data class RecordDto(@field:DateValidation val dt: String?,
                     @field:EnumValidation val level: String?,
                     val message: String?)