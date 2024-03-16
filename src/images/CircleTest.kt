package images

import SVG
import datatypes.*

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