package com.hdgh0g.prototype_hell

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import java.awt.Color
import java.awt.Toolkit
import java.util.*
import javax.swing.JFrame

@Component
@Scope("prototype")
open class ColoredFrame : JFrame() {
    private val squareSize = 200

    init {
    setSize(squareSize, squareSize)
    isVisible = true
    defaultCloseOperation = EXIT_ON_CLOSE
}

    @Autowired
    lateinit var color : Color

    fun showOnRandomPlace() {
        val width = Toolkit.getDefaultToolkit().screenSize.width
        val height = Toolkit.getDefaultToolkit().screenSize.height
        val rand = Random()
        setLocation(rand.nextInt(width - squareSize), rand.nextInt(height - squareSize))
        contentPane.background = color
        repaint()
    }


}
