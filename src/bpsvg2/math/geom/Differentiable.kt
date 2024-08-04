package bpsvg2.math.geom

import bpsvg2.math.*
import java.lang.IllegalArgumentException
import kotlin.random.Random

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

    fun approximate(maxSegments: Int, d: Double = EPS): Iterable<Curve<V>> {
        val l = mutableListOf<Curve<V>>()
        approximateSegment(l, d, t0, t1, maxSegments)
        return l
    }

    private fun approximateSegment(
        l: MutableList<Curve<V>>, d: Double,
        a: Double, b: Double,
        remaining: Int): Int {
        if (remaining < 0) {
            throw IllegalArgumentException("Cannot approximate")
        }
        val ba = b - a
        val fa = evaluate(a)
        val fpa = derivative(a)
        val fb = evaluate(b)
        val fpb = derivative(b)
        val curve = Curve(fa, fa + ba * fpa / 3, fb - ba * fpb / 3)
        for (i in 0..5) {
            val t = Random.nextDouble()
            val first = curve.evaluate(t) - evaluate(a + ba * t)
            val second = curve.derivative(t) - ba * derivative(a + ba * t)
            if ( first.norm().value > d || second.norm().value > d ) {
                val ab = (a + b) / 2
                val r = approximateSegment(l, d, a, ab, remaining)
                return approximateSegment(l, d, ab, b, r)
            }
        }
        return remaining - 1
    }
}