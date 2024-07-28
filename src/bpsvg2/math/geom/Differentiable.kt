package bpsvg2.math.geom

import bpsvg2.math.*

abstract class Differentiable<V: Vector<V>> {

    abstract val t0: Double

    abstract val t1: Double


    data class Function<W: Vector<W>> (
        override val t0: Double, override val t1: Double,
        val y: (Double) -> W, val dy: (Double) -> W): Differentiable<W>() {
        override fun evaluate(t: Double): W {
            return y(t)
        }
        override fun derivative(t: Double): W {
            return dy(t)
        }
    }

    data class Linear<W: Vector<W>> (val f: Differentiable<W>,
                                     val scale: Double, val offset: Double = 0.0): Differentiable<W>() {

        override val t0 = scale * f.t0 + offset
        override val t1 = scale * f.t1 + offset

        override fun evaluate(t: Double): W {
            return f.evaluate(t * scale + offset)
        }

        override fun derivative(t: Double): W {
            return f.derivative(t * scale + offset) * scale
        }
    }

    abstract fun evaluate(t: Double): V
    abstract fun derivative(t: Double): V

    fun scale(scale: Double, offset: Double = 0.0): Differentiable<V> {
        return Linear(this, scale, offset)
    }
}