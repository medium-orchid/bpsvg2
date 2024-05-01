package images

import bpsvg2.SVG
import bpsvg2.datatypes.*
import bpsvg2.datatypes.math2d.*
import bpsvg2.datatypes.math3d.*

fun main() {
    val viewBox = Rect.zeroCentered(10, 10)
    val color1 = "#00bfff40"
    val color2 = "#ff149340"
    val n = 10
    SVG("viewBox" to viewBox) {
        val circleID = "myCircle"
        val gradientID = "myGradient"
        define(
            circleID,
            circle("r" to 5, "fill" to url("myGradient"))
        )
        define(gradientID,
            linearGradient("gradientTransform" to Mat2D.rotate(90.deg)) {
                stop("offset" to 20.percent, "stop-color" to color1)
                stop("offset" to 80.percent, "stop-color" to color2)
            }
        )
        for (i in 0..<n) {
            use(href(circleID), styleAttribute("transform" to Quat.random()))
        }
    }.to("out/circle_smash.svg")
}