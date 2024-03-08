package images

import SVGRoot

fun main() {
    val root = SVGRoot("viewBox" to "0,0,10,10") {
        val circleID = "myCircle"
        val gradientID = "myGradient"
        define(
            circleID,
            circle("cx" to 0, "cy" to 0, "r" to 5)
        )
        define(gradientID,
            linearGradient("gradientTransform" to "rotate(90)") {
                stop("offset" to "20%", "stop-color" to "gold")
                stop("offset" to "90%", "stop-color" to "red")
            }
        )
        use(href(circleID), "x" to 5, "y" to 5, "fill" to url("myGradient"))
    }

    print(root)
}