package images

import bpsvg2.SVG
import bpsvg2.datatypes.*
import bpsvg2.datatypes.math3d.*

fun color(index: Int): String {
    return "hsla(${60 * index}, 81%, 64%, 0.5)"
}

fun main() {
    val duration = "5s"
    val view = 10.0
    val side = 5.0
    val viewBox = Rect.zeroCentered(view, view)
    SVG("viewBox" to viewBox) {
        define("face",
            rect(
                "*" to Rect.zeroCentered(side, side),
                styleAttribute("transform" to ((side / 2) * Vec3.Z).toOrtho())
            )
        )
        style {
            val axis = Vec3.randomUnit()
            select(".spin",
                "animation-duration" to duration,
                "animation-name" to "spin",
                "animation-iteration-count" to "infinite"
            )
            keyframes("spin") {
                for (i in 0..4) {
                    select("${25 * i}%",
                        "transform" to axis.axisAngle((90 * i).deg)
                    )
                }
            }
        }
        g("class" to "spin") {
            use(
                href("face"),
                "fill" to color(0),
            )
            use(
                href("face"),
                "fill" to color(1),
                styleAttribute("transform" to Vec3.X.axisAngle(90.deg))
            )
            use(
                href("face"),
                "fill" to color(2),
                styleAttribute("transform" to Vec3.X.axisAngle(180.deg))
            )
            use(
                href("face"),
                "fill" to color(3),
                styleAttribute("transform" to Vec3.X.axisAngle((-90).deg))
            )
            use(
                href("face"),
                "fill" to color(4),
                styleAttribute("transform" to Vec3.Y.axisAngle(90.deg))
            )
            use(
                href("face"),
                "fill" to color(5),
                styleAttribute("transform" to Vec3.Y.axisAngle(90.deg))
            )
        }
    }.to("out/cube.svg")
}