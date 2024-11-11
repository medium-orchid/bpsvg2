package bpsvg2.math.d2

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode

data class Trans2D(val scale: Double = 1.0, val angle: Angle = Angle.id, val offset: Vec2 = Vec2.zero) : DataType {

    companion object {
        val id = Trans2D(1.0, Angle.id)
    }

    fun approximatelyEquals(other: Trans2D): Boolean {
        return approx(scale, other.scale)
                && angle.approximatelyEquals(other.angle)
                && offset.approx(other.offset)
    }

    operator fun times(other: Trans2D): Trans2D {
        // k't'(kt + x) + x'
        // k't'kt + k't'x + x'
        val newOffset = scale * (angle * other.offset) + offset
        return Trans2D(scale * other.scale, angle + other.angle, newOffset)
    }

    operator fun times(other: Double): Trans2D {
        return Trans2D(scale * other, angle, scale * offset)
    }

    operator fun times(other: Vec2): Vec2 {
        return scale * (angle * other) + offset
    }

    operator fun plus(other: Vec2): Trans2D {
        return Trans2D(scale, angle, offset + other)
    }

    fun inverse(): Trans2D {
        // k't'(kt + x) + x' = id
        // = k't'kt + k't'x + x'
        // => k't'kt = id
        // => k'k = 1 =>  [ k' = 1/k ]
        // => t't = id => [ t' = t^-1 ]
        // =>             [ x' = -k't'x ]
        val newScale = 1.0 / scale
        val newAngle = -angle
        val newOffset = -newScale * (newAngle * offset)
        return Trans2D(newScale, newAngle, newOffset)
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
        if (!offset.approx(Vec2.zero)) {
            builder.append("translate(").append(offset).append(")")
            empty = false
        }
        if (!approx(scale, 0.0)) {
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