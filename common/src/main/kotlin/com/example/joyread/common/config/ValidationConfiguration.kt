package com.example.joyread.common.config

import jakarta.validation.Validation
import jakarta.validation.Validator
import org.hibernate.validator.HibernateValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor

@Configuration
class ValidationConfiguration {
    @Bean
    fun validator(): Validator {
        val validatorFactory =
            Validation.byProvider(HibernateValidator::class.java).configure().failFast(true).buildValidatorFactory()
        return validatorFactory.validator
    }

    @Bean
    fun methodValidationPostProcessor(): MethodValidationPostProcessor {
        val methodValidationPostProcessor = MethodValidationPostProcessor()
        methodValidationPostProcessor.setValidator(validator())
        return methodValidationPostProcessor
    }
}