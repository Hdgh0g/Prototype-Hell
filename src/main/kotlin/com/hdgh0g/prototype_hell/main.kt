package com.hdgh0g.prototype_hell

import java.awt.AWTEvent
import java.awt.Toolkit
import java.awt.event.MouseEvent
import java.awt.event.MouseEvent.MOUSE_CLICKED
import java.util.concurrent.Executors
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import kotlin.system.exitProcess


fun main(args: Array<String>) {
    val context = AnnotationConfigApplicationContext(Config::class.java)

    Toolkit.getDefaultToolkit().addAWTEventListener({ event ->
        if (event is MouseEvent && event.id == MOUSE_CLICKED) {
            context.close()
            exitProcess(0)
        }
    }, AWTEvent.MOUSE_EVENT_MASK)

    val service = Executors.newFixedThreadPool(2)

    while (true) {
        //concurrent create
        for (i in 1..2) {
            service.submit({ context.getBean(ColoredFrame::class.java) })
        }
        Thread.sleep(100)
    }
}