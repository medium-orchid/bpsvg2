package images

import bpsvg2.SVG
import bpsvg2.datatypes.*

fun main() {
    val viewBox = Rect.byCenter(Vec2.zero, 10, 10)
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
        style {
            for (i in 0..<n) {
                select(".circle$i",
                    "transform" to Quat.random()
                )
            }
        }
        for (i in 0..<n) {
            use(href(circleID), "class" to "circle$i")
        }
    }.to("out/circle_smash.svg")
}