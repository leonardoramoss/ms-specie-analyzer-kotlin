package io.species.analyzer.infrastructure.annotation

import org.springframework.stereotype.Service

@Service
@MustBeDocumented
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationService
