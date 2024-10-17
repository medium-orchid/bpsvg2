package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.geom.*
import kotlin.math.sqrt

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(1.4, 1.4),
        "**" to 500.px) {
        val iterations = 4
        val radius = sqrt(1.0/3)
        val lSystem = LSystem()
            .addOrientationConstant("s", Trans2D(1.0 / 3)) // Shrink
            .addOrientationConstant("g", Trans2D(3.0)) // Grow
            .addOrientationConstant("l", 60.deg.toTrans()) // Left 60 degrees
            .addOrientationConstant("r", (-60).deg.toTrans()) // Right 120 degrees
            .addMovementConstant("f", Vec2.X.toTrans()) // Move forward
            .addMovementConstant("b", (-Vec2.X).toTrans()) // Move backwards
            .addUseConstant("t", "triangle") // Draw the equilateral triangle
            .addAlias("c", "Cf") // Sugar for a smaller size
            .addVariable("C", "sftbcrcllcrcg")
        style {
            select("#C0")(
                "fill" to "none",
                "stroke" to "mediumorchid",
                "stroke-width" to 0.6,
                "stroke-linecap" to "round",
            )
            select("path")(
                "stroke" to "white",
                "stroke-width" to 0.007,
                "stroke-miterlimit" to 1,
                "stroke-join" to "bevel",
                "fill" to "white"
            )
        }
        defs {
            g(id("triangle")) {
                path {
                    moveTo(Vec2.zero)
                    lineTo(Vec2.X, (-60).deg.unitVector())
                    close()
                }
            }
            line(id("C0"), "%1" to Vec2.zero, "%2" to Vec2.X)
            lSystem.perform(this, iterations)
            g(id("side")) {
                val offset = 210.deg.radius(radius)
                use(href("C$iterations"), "transform" to offset.toTrans())
            }
        }
        path {
            moveTo(90.deg.radius(radius))
            lineTo(210.deg.radius(radius), 330.deg.radius(radius))
            close()
        }
        use(href("side"))
        use(href("side"), "transform" to 120.deg.toTrans())
        use(href("side"), "transform" to 240.deg.toTrans())
    }.saveTo("out/koch_l_system.svg")
}