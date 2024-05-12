package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*

fun main() {
    val viewBox = Rect(10, 10)
    SVG.root("viewBox" to viewBox) {
        val circleID = "circ"
        val gradientID = "grad"
        defs {
            circle(id(circleID), "r" to 5)
            linearGradient(id(gradientID), "gradientTransform" to Mat2D.rotate(90.deg)) {
                stop("offset" to 20.percent, "stop-color" to "gold")
                stop("offset" to 90.percent, "stop-color" to "red")
            }
        }
        use(href(circleID), viewBox.centered, "fill" to url(gradientID))
    }.saveTo("out/circle_test.svg")
}