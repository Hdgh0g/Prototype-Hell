package com.hdgh0g.prototype_hell

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import java.awt.Color
import java.util.*

@Configuration
@ComponentScan
open class Config {

    private val K = 4

    @Bean
    @Scope("prototype")
    open fun randomColor(): Color {
        val rand = Random()
        return Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255))
    }

    @Bean
    @Scope("prototype")
    open fun coloredFrame() : ColoredFrame {
        return object : ColoredFrame() {
            override fun transformColor(color: Color): Color {
                val rc = randomColor()
                return Color(
                        (rc.red + color.red * K) / (K + 1),
                        (rc.green + color.green * K) / (K + 1),
                        (rc.blue + color.blue * K) / (K + 1)
                )
            }

        }
    }

}