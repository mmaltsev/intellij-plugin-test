package com.github.mmaltsev.intellijplugintest.ui

import com.intellij.util.ui.JBUI
import java.awt.*
import javax.swing.JComponent
import javax.swing.JProgressBar
import javax.swing.plaf.basic.BasicProgressBarUI
import javax.swing.ImageIcon

class MyProgressBarUI : BasicProgressBarUI() {

    private val userPhoto: Image? by lazy {
        try {
            val url = this::class.java.getResource("/user_photo.png")
            url?.let { ImageIcon(it).image }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    
    // We override paint to fully control the rendering
    override fun paint(g: Graphics, c: JComponent) {
        if (c !is JProgressBar) {
            return
        }
        
        val g2 = g.create() as Graphics2D
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)
            
            val barRect = c.bounds
            
            // Paint background
            g2.color = c.background
            g2.fillRect(0, 0, barRect.width, barRect.height)
            
            if (userPhoto != null) {
                val imageSize = barRect.height // simple square based on height
                
                if (c.isIndeterminate) {
                   // For indeterminate, just bounce the image back and forth or tile it
                   // Let's just draw one image moving for simplicity or just centered
                   val box = getBox(barRect)
                   if (box != null) {
                       g2.drawImage(userPhoto, box.x, 0, imageSize, imageSize, c)
                   }
                } else {
                    // For determinate, clip and draw
                    val amountFull = getAmountFull(c.insets, barRect.width, barRect.height)
                    g2.clipRect(0, 0, amountFull, barRect.height)
                    
                    // distinct effect: draw the image repeatedly
                    var x = 0
                    while (x < amountFull) {
                        g2.drawImage(userPhoto, x, 0, imageSize, imageSize, c)
                        x += imageSize
                    }
                }
            } else {
                // Fallback
                if (c.isIndeterminate) {
                    paintIndeterminate(g, c)
                } else {
                    paintDeterminate(g, c)
                }
            }
        } finally {
            g2.dispose()
        }
    }
}
