package bpsvg2.datatypes

import bpsvg2.SVGBuilder

data class Ortho2D(val scale: Double, val angle: Angle, val offset: Vec2 = Vec2.zero): DataType {

    operator fun times(other: Ortho2D): Ortho2D {
        // k't'(kt + x) + x'
        // k't'kt + k't'x + x'
        val newOffset = scale * angle.toMat2D() * other.offset + offset
        return Ortho2D(scale * other.scale, angle + other.angle, newOffset)
    }

    operator fun times(other: Double): Ortho2D {
        return Ortho2D(scale * other, angle, scale * offset)
    }

    override fun put(builder: SVGBuilder) {
        var id = true
        if (approx(scale, 0.0)) {
            builder.append("scale(0)")
        } else if (!approx(scale, 1.0)){
            builder.append("scale(").append(scale).append(")")
            id = false
        }
        if (!angle.approximatelyEquals(Angle.id)) {
            if (!id) builder.append(" ")
            builder.append("rotate(").append(angle).append(")")
            id = false
        }
        if (!offset.approximatelyEquals(Vec2.zero)) {
            if (!id) builder.append(" ")
            builder.append("translate(").append(offset).append(")")
            id = false
        }
        if (id) builder.append("rotate(0)")
    }
}