package com.ut.demo.validation

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass



@MustBeDocumented
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FIELD, AnnotationTarget.TYPE, AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [(InDateRangeValidator::class)])
@Retention(AnnotationRetention.RUNTIME)
annotation class DateValidation(val message: String = "default",
                                val groups: Array<KClass<*>> = [],
                                val payload: Array<KClass<out Any>> = [])


class InDateRangeValidator : ConstraintValidator<DateValidation, String> {

    private lateinit var constraintAnnotation: DateValidation

    override fun initialize(constraintAnnotation: DateValidation) {
        this.constraintAnnotation = constraintAnnotation
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Date is null or empty").addConstraintViolation()
        }
        return try {
            LocalDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
            true
        } catch (ex: DateTimeParseException) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Date parsing error").addConstraintViolation()
            false
        }
    }
}
