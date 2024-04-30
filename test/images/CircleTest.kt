package images

import bpsvg2.SVG
import bpsvg2.datatypes.*
import bpsvg2.datatypes.math2d.*

fun main() {
    val viewBox = Rect(10, 10)
    SVG("viewBox" to viewBox) {
        val circleID = "myCircle"
        val gradientID = "myGradient"
        define(
            circleID,
            circle("r" to 5)
        )
        define(gradientID,
            linearGradient("gradientTransform" to Mat2D.rotate(90.deg)) {
                stop("offset" to 20.percent, "stop-color" to "gold")
                stop("offset" to 90.percent, "stop-color" to "red")
            }
        )
        use(href(circleID), viewBox.centered, "fill" to url("myGradient"))
    }.to("out/circle_test.svg")
}