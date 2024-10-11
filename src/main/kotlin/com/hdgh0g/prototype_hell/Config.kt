package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.interesting_bean.InterestingBean
import java.awt.Color
import java.util.function.Supplier
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import kotlin.random.Random

@Configuration
@ComponentScan
open class Config {

    @Autowired
    lateinit var interestingBean: InterestingBean

    @Bean
    @Scope("prototype")
    open fun randomColor(): Color = Color(Random.nextInt(255), Random.nextInt(255), Random.nextInt(255))

    @Bean
    @Scope("prototype")
    open fun coloredFrame(): ColoredFrame = object : ColoredFrame() {

        private val transformationCoefficient = 4

        override fun transformColor(color: Color): Color {
            val rc = randomColor()
            return Color(
                (rc.red + color.red * transformationCoefficient) / (transformationCoefficient + 1),
                (rc.green + color.green * transformationCoefficient) / (transformationCoefficient + 1),
                (rc.blue + color.blue * transformationCoefficient) / (transformationCoefficient + 1)
            )
        }
    }

    @Bean
    open fun getInterestingBeanSupplier(): Supplier<InterestingBean> {
        return Supplier { interestingBean }
    }

}