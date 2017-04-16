package com.hdgh0g.prototype_hell

import com.hdgh0g.prototype_hell.inject_size.InjectSize
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Toolkit
import java.util.*
import javax.annotation.PostConstruct
import javax.swing.JFrame

@Component
@Scope("prototype")
open class ColoredFrame : JFrame() {

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

    @Autowired
    lateinit var color: Color

    @PostConstruct
    fun showOnRandomPlace() {
        setSize(squareSize.toInt(), squareSize.toInt())
        val width = Toolkit.getDefaultToolkit().screenSize.width
        val height = Toolkit.getDefaultToolkit().screenSize.height
        val rand = Random()
        setLocation(rand.nextInt(width - squareSize.toInt()), rand.nextInt(height - squareSize.toInt()))
        contentPane.background = color
        isVisible = true
        repaint()
    }

}