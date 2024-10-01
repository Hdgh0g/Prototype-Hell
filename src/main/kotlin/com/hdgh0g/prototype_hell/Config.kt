package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.interesting_bean.InterestingBean
import java.awt.Color
import java.util.Random
import java.util.function.Supplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope

@Configuration
@ComponentScan
open class Config {

    private val K = 4

    @Autowired
    lateinit var interestingBean : InterestingBean

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

    @Bean
    open fun getInterestingBeanSupplier() : Supplier<InterestingBean> {
        return Supplier { interestingBean }
    }

}