package com.ut.demo.validation

import com.ut.demo.enums.LoggingLevel
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import kotlin.reflect.KClass


@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.VALUE_PARAMETER)
@Constraint(validatedBy = [(EnumValidator::class)])
@Retention(AnnotationRetention.RUNTIME)
annotation class EnumValidation(val message: String = "default",
                                val groups: Array<KClass<*>> = [],
                                val payload: Array<KClass<out Any>> = [])


class EnumValidator : ConstraintValidator<EnumValidation, String> {

    override fun initialize(constraintAnnotation: EnumValidation) {
    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrBlank()) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Level field is null or empty").addConstraintViolation()
            return false
        }
        return try {
            LoggingLevel.valueOf(value!!)
            true
        } catch (ex: Exception) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Illegal level value, choose one from this list: " +
                    LoggingLevel.values().joinToString { it.toString() }).addConstraintViolation()
            false
        }
    }
}