package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.inject_size.InjectSize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Toolkit
import java.util.*
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
        Timer().schedule(object : TimerTask() {
            override fun run() {
                isVisible = false
                dispose()
            }
        }, 1000)
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