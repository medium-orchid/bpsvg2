package bpsvg2.math.d3

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode

data class Ortho3D(val scale: Double, val quat: Quat, val offset: Vec3 = Vec3.zero) : DataType {

    companion object {
        val id = Ortho3D(1.0, Quat.id)
    }

    fun approximatelyEquals(other: Ortho3D): Boolean {
        return approx(scale, other.scale)
                && quat.approximatelyEquals(other.quat)
                && offset.approximatelyEquals(other.offset)
    }

    operator fun times(other: Ortho3D): Ortho3D {
        val newOffset = scale * quat * other.offset + offset
        return Ortho3D(scale * other.scale, quat * other.quat, newOffset)
    }

    operator fun times(other: Double): Ortho3D {
        return Ortho3D(scale * other, quat, scale * offset)
    }

    operator fun times(other: Vec3): Vec3 {
        return scale * (quat * (offset + other))
    }

    operator fun plus(other: Vec3): Ortho3D {
        return Ortho3D(scale, quat, offset + other)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        if (mode != OutputMode.CSS) {
            throw IllegalStateException(OutputMode.CSS.expectedModeError(mode))
        }
        if (approx(scale, 0.0)) {
            builder.append("scale(0)")
            return
        }
            var id = true
        if (!offset.approximatelyEquals(Vec3.zero)) {
            builder.append("translate3d(").append(offset, mode).append(")")
            id = false
        }
        if (!approx(scale, 1.0)) {
            if (!id) builder.append(" ")
            builder.append("scale(").append(scale, mode).append(")")
            id = false
        }
        if (!quat.approximatelyEquals(Quat.id)) {
            if (!id) builder.append(" ")
            builder.append(quat, mode)
            id = false
        }
        if (id) builder.append("rotate3d(0)")
    }
}