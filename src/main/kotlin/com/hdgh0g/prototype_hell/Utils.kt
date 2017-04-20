package com.hdgh0g.prototype_hell

import org.springframework.stereotype.Service
import java.awt.Color
import java.awt.Robot

@Service
open class Utils {

    private val robot = Robot()

    fun getColorForPixel(x : Int, y : Int) : Color {
        return robot.getPixelColor(x, y)
    }
}