package bpsvg2.math.geom

import bpsvg2.math.*

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

    fun vLine(u: Double): Curve<V> {
        return vCurve.evaluate(u)
    }

    fun evaluate(u: Double, v: Double): V {
        return uCurve.evaluate(u).evaluate(v)
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

}