package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.*
import kotlin.random.Random

fun main() {
    SVGElement.root("viewBox" to Rect.zeroCentered(2.0, 2.0),
        "**" to 500.px) {
        val ticks = 50
        val tickHalfLength = 0.04
        val pathWidth = 0.02
        val tickWidth = 0.01
        val guideWidth = 0.02
        val pointRadius = 0.02
        style {
            select("svg")(
                "background-color" to "beige"
            )
            select("path")(
                "fill" to "none",
                "stroke" to "red",
                "stroke-width" to pathWidth,
                "stroke-linecap" to "butt",
            )
            select("#ticks")(
                "stroke" to "blue",
                "stroke-width" to tickWidth,
            )
            select("#guides")(
                "stroke" to "green",
                "stroke-width" to guideWidth,
            )
            select("#points") (
                "stroke" to "none",
                "fill" to "green",
            )
        }
        val curve = Curve(Curve.CUBIC) {_ -> Vec2(
            2 * Random.nextDouble() - 1,
            2 * Random.nextDouble() - 1)
        }
        val n = curve.points.size
        g(id("guides")) {
            for (i in 0..<n - 1) {
                line("%1" to curve.points[i], "%2" to curve.points[i + 1])
            }
        }
        g(id("points")) {
            for (i in 1..<n - 1) {
                circle("*c" to curve.points[i], "r" to pointRadius)
            }
        }
        path {
            fromCurve(curve)
        }
        g(id("ticks")) {
            for (i in 0..ticks) {
                val t = 1.0 * i / ticks
                val dc = curve.derivative(t).normalized()
                val g = tickHalfLength * (90.deg * dc)
                val c = curve.evaluate(t)
                val l = Curve(c + g, c - g)
                line("*" to l)
            }
        }
    }.saveTo("out/bezier_operations.svg")
}