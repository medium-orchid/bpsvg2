package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.d3.*

fun main() {
    val viewBox = Rect.zeroCentered(10, 10)
    val color1 = "#00bfff40"
    val color2 = "#ff149340"
    val n = 10
    SVG.root("viewBox" to viewBox) {
        val circleID = "myCircle"
        val gradientID = "myGradient"
        defs {
            linearGradient(id(gradientID), "gradientTransform" to Mat2D.rotate(90.deg)) {
                stop("offset" to 20.percent, "stop-color" to color1)
                stop("offset" to 80.percent, "stop-color" to color2)
            }
            circle(id(circleID), "r" to 5, "fill" to url("myGradient"))
        }
        for (i in 0..<n) {
            use(href(circleID), styleAttribute("transform" to Quat.randomUnit()))
        }
    }.saveTo("out/circle_smash.svg")
}