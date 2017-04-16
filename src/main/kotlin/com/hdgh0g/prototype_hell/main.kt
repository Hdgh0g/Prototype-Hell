package com.hdgh0g.prototype_hell

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.awt.Color
import java.util.*


fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext(
            config::class.java,
            ColoredFrame::class.java
    )
    while (true) {
        val bean = context.getBean(ColoredFrame::class.java)
        bean.showOnRandomPlace()
        Thread.sleep(50)
    }
}

@Configuration
open class config {

    @Bean
    @Scope("prototype")
    open fun randomColor(): Color {
        val rand = Random()
        return Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
    }
}