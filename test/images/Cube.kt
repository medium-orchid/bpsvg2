package images

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.d3.*
/*
    SVGs do not enjoy 3D transformations. We do not have to agree.
    Faces do not animate together as a cube, but I like the effect.
 */
fun main() {

    val duration = "10s"

    val size = 500.px
    val side = 250.px
    val offset = ((side / 2) * Vec3.Z).toOrtho()

    val colors = Array(6) {i -> "hsla(${360 * i / 6}, 75%, 50%, 0.75)"}

    val rotations = arrayOf(
        Ortho3D.id, //Front
        Vec3.Y.axisAngle(0.25.turns), //Right
        Vec3.Y.axisAngle(0.5.turns), //Back
        Vec3.Y.axisAngle(0.75.turns), //Left
        Vec3.X.axisAngle(0.25.turns), //Top
        Vec3.X.axisAngle(0.75.turns), //Bottom
    )

    val frames = arrayOf(
        "from",
        "50%",
        "to",
    )

    SVGElement.root("**" to size) {
        style {
            select("*") (
                "transform-origin" to "center",
                "animation-iteration-count" to "infinite",
                "animation-duration" to duration,
                "animation-direction" to "alternate",
            )
            val cubeRotations = Array(frames.size) {_ -> Quat.randomUnit()}
            for (i in 0..<6) {
                keyframes("spin$i") {
                    for (j in frames.indices) {
                        select(frames[j]) (
                            "transform" to cubeRotations[j] * rotations[i] * offset
                        )
                    }
                }
            }
        }
        defs {
            rect(id("face"), "**" to side, "*" to (size - side) / 2)
        }
        for (i in 0..<6) {
            use(
                href("face"),
                "fill" to colors[i],
                styleAttribute("animation-name" to "spin$i")
            )
        }
    }.saveTo("out/cube.svg")
}