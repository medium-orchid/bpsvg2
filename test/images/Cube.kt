package images

import bpsvg2.datatypes.*
import bpsvg2.datatypes.math3d.*

fun color(index: Int): String {
    return "hsla(${60 * index}, 81%, 64%, 0.5)"
}

fun main() {
    val duration = "5s"
    val view = 200.0.px
    val side = view / 2
    val viewBox = Rect.zeroCentered(view, view)
    SVG("*" to viewBox) {
        define("face",
            rect(
                "*" to Rect.zeroCentered(side, side),
            )
        )
        g("class" to "spin") {
            val offset = Ortho3D(1.0, Quat.randomUnit(), (side / 2) * Vec3.Z)
            use(
                href("face"),
                "fill" to color(0),
                //styleAttribute("transform" to offset)
            )
            use(
                href("face"),
                "fill" to color(1),
                styleAttribute("transform" to Vec3.X.axisAngle(0.25.turns) * offset)
            )
            use(
                href("face"),
                "fill" to color(2),
                styleAttribute("transform" to Vec3.X.axisAngle(0.5.turns) * offset)
            )
            use(
                href("face"),
                "fill" to color(3),
                styleAttribute("transform" to Vec3.X.axisAngle(0.75.turns) * offset)
            )
            use(
                href("face"),
                "fill" to color(4),
                styleAttribute("transform" to Vec3.Y.axisAngle(0.25.turns) * offset)
            )
            use(
                href("face"),
                "fill" to color(5),
                styleAttribute("transform" to Vec3.Y.axisAngle(0.75.turns) * offset)
            )
        }
    }.to("out/cube.svg")
}