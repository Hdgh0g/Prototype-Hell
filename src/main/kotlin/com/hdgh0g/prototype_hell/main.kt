package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.inject_size.InjectSizeAnnotationBeanPostProcessor
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.awt.AWTEvent
import java.awt.Color
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.MOUSE_CLICKED
import java.lang.System.exit
import java.util.*
import java.util.concurrent.Executors


fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext(
            config::class.java,
            ColoredFrame::class.java,
            InjectSizeAnnotationBeanPostProcessor::class.java
    )

    Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
        event?.let {
            if (event is MouseEvent && event.id == MOUSE_CLICKED) {
                exit(0)
            }
        }
    }, AWTEvent.MOUSE_EVENT_MASK)

    val service = Executors.newFixedThreadPool(2)
    while (true) {
        //concurrent create
        for (i in 1..2) {service.submit({context.getBean(ColoredFrame::class.java)})}
        Thread.sleep(100)
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