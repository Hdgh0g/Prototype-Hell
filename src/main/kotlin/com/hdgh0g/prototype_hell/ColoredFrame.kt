package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.inject_size.InjectSize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Toolkit
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct
import javax.swing.JFrame

@Component
abstract class ColoredFrame : JFrame() {

    @Autowired
    lateinit var utils : Utils

    @InjectSize(min = 100, max = 400, step = 50)
    lateinit var squareSize : Integer

    init {
        isUndecorated = true
        Executors.newSingleThreadScheduledExecutor().schedule({
            isVisible = false
            dispose()
        }, 1, TimeUnit.SECONDS)
    }

    @PostConstruct
    fun showOnRandomPlace() {
        setSize(squareSize.toInt(), squareSize.toInt())
        val width = Toolkit.getDefaultToolkit().screenSize.width
        val height = Toolkit.getDefaultToolkit().screenSize.height
        val rand = Random()
        val x = rand.nextInt(width - squareSize.toInt())
        val y = rand.nextInt(height - squareSize.toInt())
        val colorForPixel = utils.getColorForPixel(x + squareSize.toInt() / 2, +squareSize.toInt() / 2)

        contentPane.background = transformColor(colorForPixel)
        setLocation(x, y)
        isVisible = true
        repaint()
    }

    abstract fun transformColor(color : Color) : Color
}