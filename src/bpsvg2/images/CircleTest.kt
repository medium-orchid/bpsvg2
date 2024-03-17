package bpsvg2.images

import bpsvg2.SVG
import bpsvg2.datatypes.Mat2D
import bpsvg2.datatypes.Rect
import bpsvg2.datatypes.Vec2
import bpsvg2.datatypes.percent

fun main() {
    val viewBox = Rect(10, 10)
    SVG("viewBox" to viewBox) {
        val circleID = "myCircle"
        val gradientID = "myGradient"
        define(
            circleID,
            circle("*c" to Vec2.zero, "r" to 5)
        )
        define(gradientID,
            linearGradient("gradientTransform" to Mat2D.rotate(90)) {
                stop("offset" to 20.percent, "stop-color" to "gold")
                stop("offset" to 90.percent, "stop-color" to "red")
            }
        )
        use(href(circleID), "*" to viewBox.point(Vec2.center), "fill" to url("myGradient"))
    }.to("out/circle_test.svg")
}