package bpsvg2.math.d2

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode

data class Ortho2D(val scale: Double, val angle: Angle, val offset: Vec2 = Vec2.zero) : DataType {

    companion object {
        val id = Ortho2D(1.0, Angle.id)
    }

    fun approximatelyEquals(other: Ortho2D): Boolean {
        return approx(scale, other.scale)
                && angle.approximatelyEquals(other.angle)
                && offset.approximatelyEquals(other.offset)
    }

    operator fun times(other: Ortho2D): Ortho2D {
        // k't'(kt + x) + x'
        // k't'kt + k't'x + x'
        val newOffset = scale * angle.toMat2D() * other.offset + offset
        return Ortho2D(scale * other.scale, angle + other.angle, newOffset)
    }

    operator fun times(other: Double): Ortho2D {
        return Ortho2D(scale * other, angle, scale * offset)
    }

    operator fun times(other: Vec2): Vec2 {
        return scale * (angle * other) + offset
    }

    operator fun plus(other: Vec2): Ortho2D {
        return Ortho2D(scale, angle, offset + other)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        if (approximatelyEquals(id)) {
            builder.append("scale(1)")
            return
        } else if (approx(scale, 0.0)) {
            builder.append("scale(0)")
            return
        }
        var empty = true
        if (!offset.approximatelyEquals(Vec2.zero)) {
            builder.append("translate(").append(offset).append(")")
            empty = false
        }
        if (!approx(scale, 1.0)) {
            if (empty) builder.append(" ")
            builder.append("scale(").append(scale).append(")")
            empty = false
        }
        if (!angle.approximatelyEquals(Angle.id)) {
            if (empty) builder.append(" ")
            builder.append("rotate(").append(angle).append(")")
        }
    }
}