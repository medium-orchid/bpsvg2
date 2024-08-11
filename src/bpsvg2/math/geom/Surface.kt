package bpsvg2.math.geom

import bpsvg2.math.*
import bpsvg2.math.d2.*

class Surface<V : Vector<V>> private constructor(
    private val uCurve: Curve<Curve<V>>, private val vCurve: Curve<Curve<V>>
) : Vector<Surface<V>> {

    constructor(uCurve: Curve<Curve<V>>) : this(uCurve, Curve(uCurve.degree) { i ->
        Curve(uCurve.points[0].degree) { j ->
            uCurve.points[j].points[i]
        }
    })

    constructor(uDegree: Int, init: (Int) -> Curve<V>): this(Curve(uDegree, init))

    constructor(uDegree: Int, vDegree: Int, init: (Int, Int) -> V) : this(Curve(uDegree) { i ->
        Curve(vDegree) { j ->
            init(i, j)
        }
    })

    fun uLine(u: Double): Curve<V> {
        return uCurve.evaluate(u)
    }

    fun vLine(v: Double): Curve<V> {
        return vCurve.evaluate(v)
    }

    fun evaluate(u: Double, v: Double): V {
        return uCurve.evaluate(u).evaluate(v)
    }

    fun uDerivative(u: Double, v: Double): V {
        return vCurve.evaluate(v).derivative(u)
    }

    fun vDerivative(u: Double, v: Double): V {
        return uCurve.evaluate(u).derivative(v)
    }

    fun chain(uvCurve: Differentiable<Vec2>): Differentiable<V> {
        return Differentiable.Function(uvCurve.t0, uvCurve.t1, { t ->
            val input = uvCurve.evaluate(t)
            val u = input.x.convertValue(CSSUnits.UNITLESS)
            val v = input.y.convertValue(CSSUnits.UNITLESS)
            evaluate(u, v)
        }, { t ->
            val input = uvCurve.evaluate(t)
            val d = uvCurve.derivative(t)
            val u = input.x.convertValue(CSSUnits.UNITLESS)
            val v = input.y.convertValue(CSSUnits.UNITLESS)
            val du = d.x.convertValue(CSSUnits.UNITLESS)
            val dv = d.y.convertValue(CSSUnits.UNITLESS)
            du * uDerivative(u, v) + dv * vDerivative(u, v)
        })
    }

    override fun plus(other: Surface<V>): Surface<V> {
        return Surface(uCurve + other.uCurve, vCurve + other.vCurve)
    }

    override fun minus(other: Surface<V>): Surface<V> {
        return Surface(uCurve - other.uCurve, vCurve - other.vCurve)
    }

    override fun times(other: Double): Surface<V> {
        return Surface(uCurve * other, vCurve * other)
    }

    override fun div(other: Double): Surface<V> {
        return Surface(uCurve / other, vCurve / other)
    }

    override fun norm(): Dimension {
        var n = uCurve.points[0].norm()
        for (i in 1..<uCurve.points.size) {
            n += uCurve.points[i].norm()
        }
        return n
    }
}