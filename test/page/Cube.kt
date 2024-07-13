package page

import bpsvg2.*
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.d3.*

fun main() {
    val size = 200.px
    val fontSize = 120.px
    val font = "sans-serif"
    val textColor = "white"

    val colors = Array(6) {i -> "hsla(${360 * i / 6}, 75%, 50%, 0.75)"}
    val offset = (size / 2 * Vec3.Z).toTrans()

    val rotations = arrayOf(
        Trans3D.id, //Front
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

    HTMLElement.root("lang" to "en-US") {
        head {
            title { string("Cube example") }
            style {
                byID("example")(
                    "margin" to size / 2,
                    "**" to size,
                    "transform-style" to "preserve-3d",
                    "animation-iteration-count" to "infinite",
                    "animation-duration" to "10s",
                    "animation-name" to "spin",
                    "animation-direction" to "alternate",
                )
                keyframes("spin") {
                    for (i in frames) {
                        select(i) (
                            "transform" to Trans3D(1.0, Quat.randomUnit())
                        )
                    }
                }
                byClass("face")(
                    "display" to "flex",
                    "align-items" to "center",
                    "justify-content" to "center",
                    "**" to 100.pct,
                    "position" to "absolute",
                    "backface-visibility" to "inherit",
                    "font-size" to fontSize,
                    "font-family" to font,
                    "color" to textColor,
                )
                for (i in 0..<6) {
                    byClass("n${i + 1}") (
                        "background" to colors[i],
                        "transform" to rotations[i] * offset,
                    )
                }
            }
        }
        body {
            section(id("example")) {
                for (i in 1..6) {
                    div("class" to "face n$i") {
                        string(i)
                    }
                }
            }
        }
    }.saveTo("out/cube.html")
}