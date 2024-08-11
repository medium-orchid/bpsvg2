package bpsvg2.math.geom

import bpsvg2.math.*

class Curve<V: Vector<V>>(val points: List<V>): Vector<Curve<V>>, Differentiable<V>() {

    override val t0 = 0.0
    override val t1 = 1.0

    companion object {
        const val LINEAR = 1
        const val QUADRATIC = 2
        const val CUBIC = 3
    }

    constructor(degree: Int, init: (Int) -> V): this(List(degree + 1, init))

    constructor(vararg points: V): this(points.toList())

    val degree: Int = points.size - 1

    override fun plus(other: Curve<V>): Curve<V> {
        return if (degree == other.degree) {
            Curve(points.mapIndexed { i, v ->  v + other.points[i]})
        } else if (degree > other.degree) {
            this + other.elevate(degree)
        } else {
            this.elevate(other.degree) + other
        }
    }

    override fun minus(other: Curve<V>): Curve<V> {
        return if (degree == other.degree) {
            Curve(points.mapIndexed { i, v ->  v - other.points[i]})
        } else if (degree > other.degree) {
            this + other.elevate(degree)
        } else {
            this.elevate(other.degree) + other
        }
    }

    override fun times(other: Double): Curve<V> {
        return Curve(points.map { v -> v * other })
    }

    override fun div(other: Double): Curve<V> {
        return Curve(points.map { v -> v / other })
    }

    fun elevate(target: Int): Curve<V> {
        if (target < degree) {
            throw IllegalArgumentException("cannot raise $degree curve to $target")
        } else if (target == degree) {
            return this
        } else {
            val k0 = degree.toDouble() / (degree + 1)
            val k1 = 1 - k0
            val list = List(points.size + 1) { i ->
                when (i) {
                    0 -> points[0]
                    degree + 1 -> points[degree]
                    else -> k0 * points[i - 2] + k1 * points[i - 1]
                }
            }
            return Curve(list).elevate(target)
        }
    }

    fun reversed(): Curve<V> {
        return Curve(points.reversed())
    }

    fun deCasteljau(t: Double): List<V> {
        val arr = points.toMutableList()
        val n = arr.size
        for (j in 1..<n) {
            for (k in 0..<(n - j)) {
                arr[k] = (1 - t) * arr[k] + t * arr[k + 1]
            }
        }
        return arr
    }

    override fun evaluate(t: Double): V {
        return deCasteljau(t)[0]
    }

    fun derivative(): Curve<V> {
        return Curve(List(points.size - 1) {i ->
            degree * (points[i + 1] - points[i])
        })
    }

    override fun derivative(t: Double): V {
        return derivative().evaluate(t)
    }

    override fun norm(): Dimension {
        var n = points[0].norm()
        for (i in 1..<points.size) {
            n += points[i].norm()
        }
        return n
    }

    fun splitT1(t: Double): Curve<V> {
        return Curve(deCasteljau(t))
    }

    fun split0T(t: Double): Curve<V> {
        return reversed().splitT1(t).reversed()
    }

    fun split(t0: Double, t1: Double): Curve<V> {
        if (t1 < t0) return split(t1, t0).reversed()
        val secondSplit = (t1 - t0) / (1 - t0)
        return splitT1(t0).split0T(secondSplit)
    }
}