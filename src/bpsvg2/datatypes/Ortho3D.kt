package bpsvg2.datatypes

import bpsvg2.SVGBuilder

data class Ortho3D(val scale: Double, val quat: Quat, val offset: Vec3 = Vec3.zero): DataType {

    operator fun times(other: Ortho3D): Ortho3D {
        val newOffset = scale * quat * other.offset + offset
        return Ortho3D(scale * other.scale, quat * other.quat, newOffset)
    }

    operator fun times(other: Double): Ortho3D {
        return Ortho3D(scale * other, quat, scale * offset)
    }

    override fun put(builder: SVGBuilder) {
        var id = true
        if (approx(scale, 0.0)) {
            builder.append("scale(0)")
        } else if (!approx(scale, 1.0)){
            builder.append("scale(").append(scale).append(")")
            id = false
        }
        if (!quat.approximatelyEquals(Quat.unitR)) {
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