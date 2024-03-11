package images

import datatypes.*
import SVGRoot

fun main() {
    SVGRoot(
        "width" to 600.px, "height" to 600.px,
        "viewBox" to Rect(Vec2(-300, -300),600, 600)
    ) {
        style {
            svg("background-color" to "beige")
            select("#heading",
                "font-size" to 24.px,
                "font-weight" to "bold",
            )
            select("#caption",
                "font-size" to 12.px,
            )
            select("#flower:hover",
                "cursor" to "crosshair",
            )
            select("#fade-stop-1",
                "stop-color" to "blue",
            )
            select("#fade-stop-2",
                "stop-color" to "white",
            )
            select(".segment-fill",
                "fill" to get("segment-fill-fill")
            )
        }
        define("fade", radialGradient(
            "!c" to Vec2.zero, "r" to 200,
            "gradientUnits" to "userSpaceOnUse"
        ) {
            stop("id" to "fade-stop-1", "offset" to 33.percent)
            stop("id" to "fade-stop-2", "offset" to 95.percent)
        })
    }.to("out/petals.svg")
}