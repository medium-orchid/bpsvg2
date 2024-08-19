package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.*

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(4.0, 4.0),
        "**" to 500.px) {
        val lSystem = LSystem()
            .addOrientationConstant("s", Mat2D.scale(1.0 / 3)) // Shrink
            .addOrientationConstant("g", Mat2D.scale(3.0)) // Grow
            .addOrientationConstant("l", 60.deg.toMat2D()) // Left 60 degrees
            .addOrientationConstant("r", (-120).deg.toMat2D()) // Right 120 degrees
            .addMovementConstant("f", Mat2D.offset(Vec2.X)) // Move forward
            .addUseConstant("T", "triangle") // Draw the equilateral triangle
            .addAlias("c", "sCgf") // Sugar for a smaller size
            .addVariable("C", "cTlcrclc")
        defs {
            g(id("triangle"))
            lSystem.perform(this, 4)
        }
        use(href("C3"))
    }.saveTo("out/koch_l_system.svg")
}