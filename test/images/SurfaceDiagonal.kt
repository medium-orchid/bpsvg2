package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.*

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(2.0, 2.0),
        "**" to 500.px) {
        val guides = 20
        style {
            select("svg")(
                "background-color" to "black",
            )
            select("#guides")(
                "stroke-width" to 0.005,
                "fill" to "none"
            )
            select("#u")(
                "stroke" to "blue",
            )
            select("#v")(
                "stroke" to "red",
            )
            select("#diagonal")(
                "fill" to "none",
                "stroke" to "white",
                "stroke-width" to 0.02,
            )
        }
        val surface = Surface(Curve.CUBIC, Curve.CUBIC) { _,_ ->
            Vec2.randomUnit()
        }
        val path = Curve(Vec2.zero, Vec2.X + Vec2.Y)
        val perTick = 1.0 / (guides - 1)
        path(id("diagonal")) {
            fromCurves(surface.chain(path).approximate(4))
        }
        g(id("guides")) {
            g(id("u")) {
                for (i in 0..<guides) {
                    path { fromCurve(surface.uLine(i * perTick)) }
                }
            }
            g(id("v")) {
                for (i in 0..<guides) {
                    path { fromCurve(surface.vLine(i * perTick)) }
                }
            }
        }
    }.saveTo("out/surface_diagonal.svg")
}