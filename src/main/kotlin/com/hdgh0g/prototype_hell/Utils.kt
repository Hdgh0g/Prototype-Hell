package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.interesting_bean.InterestingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Robot
import java.util.function.Supplier
import javax.annotation.PreDestroy

@Service
open class Utils {

    @Autowired
    lateinit var interestingMethodSupplier : Supplier<InterestingBean>

    private val robot = Robot()

    fun getColorForPixel(x : Int, y : Int) : Color {
        return robot.getPixelColor(x, y)
    }

    @PreDestroy
    fun destroy() {
        println("Destroying Utils")
        interestingMethodSupplier.get().doSomethingInteresting()
    }
}