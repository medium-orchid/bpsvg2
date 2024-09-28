package bpsvg2.math

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.d2.Vec2
import bpsvg2.eat.OutputMode

data class Rect(val topLeft: Vec2, val width: Dimension, val height: Dimension) : DataType {

    constructor(width: Dimension, height: Dimension) : this(Vec2.zero, width, height)

    constructor(topLeft: Vec2, width: Double, height: Double) : this(topLeft, width.d, height.d)

    constructor(width: Double, height: Double) : this(Vec2.zero, width, height)

    companion object {
        fun byCenter(center: Vec2, width: Dimension, height: Dimension): Rect {
            val diagonal = Vec2(width, height) / 2.0
            return Rect(center - diagonal, width, height)
        }

        fun byCenter(center: Vec2, width: Double, height: Double): Rect {
            val diagonal = Vec2(width, height) / 2.0
            return Rect(center - diagonal, width, height)
        }

        fun zeroCentered(width: Dimension, height: Dimension): Rect {
            return byCenter(Vec2.zero, width, height)
        }

        fun zeroCentered(width: Double, height: Double): Rect {
            return byCenter(Vec2.zero, width, height)
        }
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        topLeft.put(builder, mode)
        builder.append(',')
        width.put(builder, mode)
        builder.append(',')
        height.put(builder, mode)
    }

    override fun toString(): String {
        return "Rect(${topLeft.x}, ${topLeft.y})($width, $height)"
    }

    operator fun plus(other: Vec2): Rect {
        return Rect(topLeft + other, width, height)
    }

    fun point(vec: Vec2): Vec2 {
        return topLeft + Vec2(
            width * vec.x.convertValue(CSSUnits.PERCENT) / 100.0,
            height * vec.y.convertValue(CSSUnits.PERCENT) / 100.0
        )
    }

    val centered: Pair<String, Vec2> get() = "*" to point(Vec2.center)
}