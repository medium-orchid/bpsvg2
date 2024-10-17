package bpsvg2.geom
import bpsvg2.math.*
import bpsvg2.math.d2.*
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

    data class Mat2DTransformed(val f: Differentiable<Vec2>, val m: Mat2D): Differentiable<Vec2>() {
        override val t0 = f.t0
        override val t1 = f.t1

        override fun evaluate(t: Double): Vec2 {
            return m * f.evaluate(t)
        }

        override fun derivative(t: Double): Vec2 {
            return m * f.derivative(t)
        }
    }

    data class Trans2DTransformed(val f: Differentiable<Vec2>, val t: Trans2D): Differentiable<Vec2>() {
        override val t0 = f.t0
        override val t1 = f.t1

        override fun evaluate(t: Double): Vec2 {
            return t * f.evaluate(t)
        }

        override fun derivative(t: Double): Vec2 {
            return t * f.derivative(t)
        }
    }

    abstract fun evaluate(t: Double): V
    abstract fun derivative(t: Double): V

    fun scale(scale: Double, offset: Double = 0.0): Differentiable<V> {
        return Linear(this, scale, offset)
    }

    fun approximate(maxDepth: Int, d: Double = EPS): Iterable<Curve<V>> {
        val l = mutableListOf<Curve<V>>()
        approximateSegment(l, d, t0, t1, maxDepth)
        return l
    }

    private fun approximateSegment(
        l: MutableList<Curve<V>>, d: Double,
        a: Double, b: Double,
        remaining: Int) {
        val ba = b - a
        val fa = evaluate(a)
        val fpa = derivative(a)
        val fb = evaluate(b)
        val fpb = derivative(b)
        val curve = Curve(fa, fa + ba * fpa / 3, fb - ba * fpb / 3, fb)
        if (remaining <= 0) {
            l.add(curve)
            return
        }
        for (i in 0..5) {
            val t = Random.nextDouble()
            val first = curve.evaluate(t) - evaluate(a + ba * t)
            val second = curve.derivative(t) - ba * derivative(a + ba * t)
            if ( first.norm().value > d || second.norm().value > d ) {
                val ab = (a + b) / 2
                approximateSegment(l, d, a, ab, remaining - 1)
                approximateSegment(l, d, ab, b, remaining - 1)
                return
            }
        }
        l.add(curve)
    }
}