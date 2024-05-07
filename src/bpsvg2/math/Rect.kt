package bpsvg2.math

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.d2.Vec2
import bpsvg2.eat.OutputMode

data class Rect(val topLeft: Vec2, val width: Length, val height: Length) : DataType {

    constructor(width: Length, height: Length) : this(Vec2.zero.u(width.unit), width, height)

    constructor(topLeft: Vec2, width: Double, height: Double) : this(topLeft, width.length, height.length)

    constructor(width: Double, height: Double) : this(Vec2.zero, width, height)

    constructor(topLeft: Vec2, width: Int, height: Int) : this(topLeft, width.length, height.length)

    constructor(width: Int, height: Int) : this(Vec2.zero, width, height)

    init {
        if (topLeft.unit != width.unit || width.unit != height.unit)
            throw IllegalArgumentException("Units for $topLeft, $width, and $height do not match.")
    }

    companion object {
        fun byCenter(center: Vec2, width: Length, height: Length): Rect {
            val diagonal = Vec2(width, height) / 2.0
            return Rect(center - diagonal, width, height)
        }

        fun byCenter(center: Vec2, width: Double, height: Double): Rect {
            val diagonal = Vec2(width, height) / 2.0
            return Rect(center - diagonal, width, height)
        }

        fun byCenter(center: Vec2, width: Int, height: Int): Rect {
            val diagonal = Vec2(width, height) / 2.0
            return Rect(center - diagonal, width, height)
        }

        fun zeroCentered(width: Length, height: Length): Rect {
            return byCenter(Vec2.zero, width, height)
        }

        fun zeroCentered(width: Double, height: Double): Rect {
            return byCenter(Vec2.zero, width, height)
        }

        fun zeroCentered(width: Int, height: Int): Rect {
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

    fun point(vec: Vec2): Vec2 {
        if (vec.unit != "%") throw IllegalArgumentException("Vector $vec is not a percent vector")
        return topLeft + Vec2(width * vec.x / 100.0, height * vec.y / 100.0)
    }

    val centered: Pair<String, Vec2>
        get() {
            return "*" to point(Vec2.center)
        }
}