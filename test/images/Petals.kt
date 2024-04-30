package images

import bpsvg2.SVG
import bpsvg2.datatypes.*
import bpsvg2.datatypes.math2d.*

fun main() {
    SVG(
        "width" to 600.px, "height" to 600.px,
        "viewBox" to Rect.byCenter(Vec2.zero, 600, 600)
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
                "fill" to get("segment-fill-fill"),
            )
            select(".segment-fill:hover",
                "fill" to get("segment-fill-fill-hover"),
                "stroke" to get("segment-fill-stroke-hover")
            )
            select(".segment-edge",
                "fill" to get("segment-edge-fill"),
                "stroke" to get("segment-edge-stroke"),
                "stroke-width" to get("segment-edge-stroke-width")
            )
            select("#outer-petals",
                "opacity" to 0.75,
                set("segment-fill-fill") to "azure",
                set("segment-fill-fill-hover") to "plum",
                set("segment-fill-stroke") to "lightsteelblue",
                set("segment-fill-stroke-hover") to "none",
                set("segment-fill-stroke-width") to 1,
                set("segment-edge-fill") to "none",
                set("segment-edge-stroke") to "deepskyblue",
                set("segment-edge-stroke-hover") to "slateblue",
                set("segment-edge-stroke-width") to 3,
            )
            select("#inner-petals",
                set("segment-fill-fill") to "yellow",
                set("segment-fill-fill-hover") to "darkseagreen",
                set("segment-fill-stroke") to "yellow",
                set("segment-fill-stroke-hover") to "none",
                set("segment-fill-stroke-width") to 1,
                set("segment-edge-fill") to "none",
                set("segment-edge-stroke") to "yellowgreen",
                set("segment-edge-stroke-hover") to "none",
                set("segment-edge-stroke-width") to 9,
            )
        }
        title {
            string("SVG demonstration")
        }
        desc {
            string("Mozilla CSS Getting Started - SVG demonstration")
        }
        define("fade", radialGradient(
            "*c" to Vec2.zero, "r" to 200,
            "gradientUnits" to "userSpaceOnUse"
        ) {
            stop("id" to "fade-stop-1", "offset" to 33.percent)
            stop("id" to "fade-stop-2", "offset" to 95.percent)
        })
        define("segment", g("class" to "segment") {
            path("class" to "segment-fill") {
                moveTo(Vec2.zero)
                verticalBy(-200)
                arcBy(40, 40, 0, largeArc = false, clockwise = false, Vec2(-62, 10))
                close()
            }
            path("class" to "segment-edge") {
                moveTo(Vec2(0, -200))
                arcBy(40, 40, 0, largeArc = false, clockwise = false, Vec2(-62, 10))
            }
        })
        define("quadrant", g {
            for (i in 0..<5) {
                use(href("segment"), "transform" to (i * 18.deg).toOrtho())
            }
        })
        define("petals", g {
            for (i in 0..<4) {
                use(href("quadrant"), "transform" to (i * 90.deg).toOrtho()) {
                }
            }
        })
        text("id" to "heading", "*" to Vec2(-280, -270)) {
            string("SVG demonstration")
        }
        text("id" to "caption", "*" to Vec2(-280, -250)) {
            string("Move your mouse pointer over the flower.")
        }
        g("id" to "flower") {
            circle("id" to "overlay",
                "*c" to Vec2.zero, "r" to 200,
                "stroke" to "none",
                "fill" to url("fade"),
            )
            use("id" to "outer-petals", href("petals"))
            use("id" to "inner-petals", href("petals"),
                "transform" to Ortho2D(1.0/3, 9.deg)
            )
        }
    }.to("out/petals.svg")
}