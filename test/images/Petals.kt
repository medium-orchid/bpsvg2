package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*

fun main() {
    SVGElement.root(
        "width" to 600.px, "height" to 600.px,
        "viewBox" to Rect.byCenter(Vec2.zero, 600.0, 600.0)
    ) {
        style {
            select("svg")(
                "background-color" to "beige"
            )
            select("#heading")(
                "font-size" to 24.px,
                "font-weight" to "bold",
            )
            select("#caption")(
                "font-size" to 12.px,
            )
            select("#flower:hover")(
                "cursor" to "crosshair",
            )
            select("#fade-stop-1")(
                "stop-color" to "blue",
            )
            select("#fade-stop-2")(
                "stop-color" to "white",
            )
            select(".segment-fill")(
                "fill" to get("segment-fill-fill"),
            )
            select(".segment-fill:hover")(
                "fill" to get("segment-fill-fill-hover"),
                "stroke" to get("segment-fill-stroke-hover")
            )
            select(".segment-edge")(
                "fill" to get("segment-edge-fill"),
                "stroke" to get("segment-edge-stroke"),
                "stroke-width" to get("segment-edge-stroke-width")
            )
            select("#outer-petals")(
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
            select("#inner-petals")(
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
        defs {
            radialGradient(
                id("fade"),
                "*c" to Vec2.zero, "r" to 200,
                "gradientUnits" to "userSpaceOnUse"
            ) {
                stop("id" to "fade-stop-1", "offset" to 33.pct)
                stop("id" to "fade-stop-2", "offset" to 95.pct)
            }
            g("class" to "segment", id("segment")) {
                path("class" to "segment-fill") {
                    moveTo(Vec2.zero)
                    verticalBy((-200).d)
                    arcBy(40, 40, 0.deg, largeArc = false, clockwise = false, Vec2(-62.0, 10.0))
                    close()
                }
                path("class" to "segment-edge") {
                    moveTo(-200 * Vec2.Y)
                    arcBy(40, 40, 0.deg, largeArc = false, clockwise = false, Vec2(-62.0, 10.0))
                }
            }
            g(id("quadrant")) {
                for (i in 0..<5) {
                    use(href("segment"), "transform" to (i * 18.deg).toTrans())
                }
            }
            g(id("petals")) {
                for (i in 0..<4) {
                    use(href("quadrant"), "transform" to (i * 90.deg).toTrans()) {
                    }
                }
            }
        }
        text(id("heading"), "*" to Vec2(-280.0, -270.0)) {
            string("SVG demonstration")
        }
        text(id("caption"), "*" to Vec2(-280.0, -250.0)) {
            string("Move your mouse pointer over the flower.")
        }
        g(id("flower")) {
            circle(
                id("overlay"),
                "*c" to Vec2.zero, "r" to 200,
                "stroke" to "none",
                "fill" to url("fade"),
            )
            use(id("outer-petals"), href("petals"))
            use(
                id("inner-petals"), href("petals"),
                "transform" to Trans2D(1.0 / 3, 9.deg)
            )
        }
    }.saveTo("out/petals.svg")
}