package bpsvg2.math.geom

import bpsvg2.math.*

abstract class Differentiable<V: Vector<V>> {
    data class Function<W: Vector<W>> (val y: (Double) -> W, val dy: (Double) -> W): Differentiable<W>() {
        override fun evaluate(t: Double): W {
            return y(t)
        }
        override fun derivative(t: Double): W {
            return dy(t)
        }
    }

    abstract fun evaluate(t: Double): V
    abstract fun derivative(t: Double): V
}