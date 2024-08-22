package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.*

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(2.0, 2.0),
        "**" to 500.px) {
        val iterations = 2
        val lSystem = LSystem()
            .addOrientationConstant("s", Trans2D(1.0 / 3)) // Shrink
            .addOrientationConstant("g", Trans2D(3.0)) // Grow
            .addOrientationConstant("l", 60.deg.toTrans()) // Left 60 degrees
            .addOrientationConstant("r", (-60).deg.toTrans()) // Right 120 degrees
            .addMovementConstant("f", Vec2.X.toTrans()) // Move forward
            .addUseConstant("t", "triangle") // Draw the equilateral triangle
            .addAlias("c", "Cf") // Sugar for a smaller size
            .addVariable("C", "sctrcllcrcg")
        style {
            select("#C0")(
                "fill" to "none",
                "stroke" to "red",
                "stroke-width" to 0.2,
                "stroke-linecap" to "round",
            )
        }
        defs {
            g(id("triangle")) {
                path("stroke" to "white", "stroke-width" to 0.01, "fill" to "white") {
                    moveTo(Vec2.zero)
                    lineTo(Vec2.X, (-60).deg.unitVector())
                    close()
                }
            }
            line(id("C0"), "%1" to Vec2.zero, "%2" to Vec2.X)
            lSystem.perform(this, iterations)
        }
        use(href("C$iterations"))
    }.saveTo("out/koch_l_system.svg")
}