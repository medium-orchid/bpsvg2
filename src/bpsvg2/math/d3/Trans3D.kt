package bpsvg2.math.d3

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode

data class Trans3D(val scale: Double, val quat: Quat, val offset: Vec3 = Vec3.zero) : DataType {

    companion object {
        val id = Trans3D(1.0, Quat.id)
    }

    fun approximatelyEquals(other: Trans3D): Boolean {
        return approx(scale, other.scale)
                && quat.approximatelyEquals(other.quat)
                && offset.approximatelyEquals(other.offset)
    }

    fun toMat3D(): Mat3D {
        return offset.toMat3D() * Mat3D.scale(scale) * quat.toMat3D()
    }

    operator fun times(other: Trans3D): Trans3D {
        val newOffset = scale * (quat * other.offset) + offset
        return Trans3D(scale * other.scale, quat * other.quat, newOffset)
    }

    operator fun times(other: Double): Trans3D {
        return Trans3D(scale * other, quat, other * offset)
    }

    operator fun times(other: Vec3): Vec3 {
        return scale * (quat * other) + offset
    }

    operator fun plus(other: Vec3): Trans3D {
        return Trans3D(scale, quat, offset + other)
    }

    operator fun minus(other: Vec3): Trans3D {
        return Trans3D(scale, quat, offset - other)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        if (mode != OutputMode.CSS) {
            throw IllegalStateException(OutputMode.CSS.expectedModeError(mode))
        }
        if (approx(scale, 0.0)) {
            builder.append("scale(0)")
            return
        } else if (approximatelyEquals(id)) {
            builder.append("scale(1)")
            return
        }
        var empty = true
        if (!offset.approximatelyEquals(Vec3.zero)) {
            val x0 = approx(offset.x, zero)
            val y0 = approx(offset.y, zero)
            val z0 = approx(offset.z, zero)
            if (x0 && y0) {
                builder.append("translateZ(").append(offset.z, mode).append(")")
            } else if (y0 && z0) {
                builder.append("translateX(").append(offset.x, mode).append(")")
            } else if (x0 && z0) {
                builder.append("translateY(").append(offset.y, mode).append(")")
            } else {
                builder.append("translate3d(").append(offset, mode).append(")")
            }
            empty = false
        }
        if (!approx(scale, 1.0)) {
            if (!empty) builder.append(" ")
            builder.append("scale(").append(scale, mode).append(")")
            empty = false
        }
        if (!quat.approximatelyEquals(Quat.id)) {
            if (!empty) builder.append(" ")
            builder.append(quat, mode)
            empty = false
        }
    }
}