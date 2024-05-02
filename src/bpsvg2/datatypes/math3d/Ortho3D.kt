package bpsvg2.datatypes.math3d

import bpsvg2.OutputBuilder
import bpsvg2.datatypes.*

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

    override fun put(builder: OutputBuilder) {
        /*val out = scale * quat.toMat3D() * offset.toMat3D()
        println(out)
        (out).put(builder)*/
        builder.cssOnly("Ortho3D")
        var id = true
        if (approx(scale, 0.0)) {
            builder.append("scale(0)")
        } else if (!approx(scale, 1.0)) {
            builder.append("scale(").append(scale).append(")")
            id = false
        }
        if (!quat.approximatelyEquals(Quat.id)) {
            if (!id) builder.append(" ")
            builder.append(quat)
            id = false
        }
        if (!offset.approximatelyEquals(Vec3.zero)) {
            if (!id) builder.append(" ")
            builder.append("translate3d(").append(offset).append(")")
            id = false
        }
        if (id) builder.append("rotate3d(0)")
    }
}