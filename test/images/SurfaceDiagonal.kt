package images

import bpsvg2.*
import bpsvg2.geom.*
import bpsvg2.math.*
import bpsvg2.math.d2.*

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(2.0, 2.0),
        "**" to 500.px) {
        val guides = 20
        val depth = 2
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
            select("#diagonal1")(
                "fill" to "none",
                "stroke" to "white",
                "stroke-width" to 0.02,
            )
            select("#diagonal2")(
                "fill" to "none",
                "stroke" to "yellow",
                "stroke-width" to 0.02,
            )
        }
        val surface = Surface(Curve.QUADRATIC, Curve.QUADRATIC) { _,_ ->
            Vec2.randomUnit()
        }
        val perTick = 1.0 / (guides - 1)
        path(id("diagonal1")) {
            fromCurves(
                surface.chain(Curve(Vec2.zero, Vec2.X + Vec2.Y))
                    .approximate(depth)
            )
        }
        path(id("diagonal2")) {
            fromCurves(
                surface.chain(Curve(Vec2.X, Vec2.Y))
                    .approximate(depth)
            )
        }
        g(id("guides")) {
            g(id("v")) {
                for (i in 0..<guides) {
                    path { fromCurve(surface.vLine(i * perTick)) }
                }
            }
            g(id("u")) {
                for (i in 0..<guides) {
                    path { fromCurve(surface.uLine(i * perTick)) }
                }
            }
        }
    }.saveTo("out/surface_diagonal.svg")
}