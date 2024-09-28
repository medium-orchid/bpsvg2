package bpsvg2.math.geom

import bpsvg2.math.*
import bpsvg2.math.d2.turns

class Ellipse<V: Vector<V>>(val r0: V, val r1: V): Vector<Ellipse<V>>, Differentiable<V>() {

    override val t0 = 0.0
    override val t1 = 1.0

    override fun evaluate(t: Double): V {
        val angle = t.turns
        return r0 * angle.sin() + r1 * angle.cos()
    }

    override fun derivative(t: Double): V {
        val angle = t.turns
        return r0 * angle.cos() - r1 * angle.sin()
    }

    override fun plus(other: Ellipse<V>): Ellipse<V> {
        return Ellipse(r0 + other.r0, r1 + other.r1)
    }

    override fun minus(other: Ellipse<V>): Ellipse<V> {
        return Ellipse(r0 - other.r0, r1 - other.r1)
    }

    override fun times(other: Double): Ellipse<V> {
        return Ellipse(other * r0, other * r1)
    }

    override fun div(other: Double): Ellipse<V> {
        return Ellipse(r0 / other, r1 / other)
    }

    override fun norm(): Dimension {
        return r0.norm() + r1.norm()
    }
}