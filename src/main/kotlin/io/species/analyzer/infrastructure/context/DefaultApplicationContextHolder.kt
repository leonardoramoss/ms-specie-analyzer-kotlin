package io.species.analyzer.infrastructure.context

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

class DefaultApplicationContextHolder {

    @Component
    companion object : ApplicationContextAware {

        @JvmField var applicationContext: ApplicationContext? = null

        override fun setApplicationContext(applicationContext: ApplicationContext) {
            this.applicationContext = applicationContext
        }

        fun getBean(beanName: String?): Any? = getBean(beanName, Any::class.java)

        fun <T> getBean(beanName: String?, clazz: Class<T>): T? {
            return beanName
                ?.let { bean -> applicationContext?.getBean(bean, clazz) }
                ?: applicationContext?.getBean(clazz)
        }
    }
}