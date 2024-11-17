package bpsvg2.geom.discrete

import bpsvg2.math.*

data class Line<V: Vector<V>>(val x0: V, val x1: V) {
    fun norm(): Dimension {
        return (x0 - x1).norm()
    }
    fun midpoint(): V {
        return (x0 + x1) / 2
    }
}