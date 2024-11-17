package bpsvg2.geom.discrete

import bpsvg2.math.*

class Polygon<V: Vector<V>>(val vertices: List<V>): Iterable<Line<V>> {

    override fun iterator(): Iterator<Line<V>> {
        return iterator {
            val n = vertices.size
            for (i in vertices.indices) {
                yield(Line(vertices[i], vertices[(i + 1) % n]))
            }
        }
    }

    fun vertexAverage(): V {
        var c = vertices[0]
        var d = 0.0
        for (i in 1..<vertices.size) {
            c += vertices[i]

        }
        return c / vertices.size
    }

    fun centroid(): V {
        var n = vertices[0].zero()
        var d = 0.d
        for (i in this) {
            val norm = i.norm()
            n += norm * i.midpoint()
            d += norm
        }
        return n / d
    }
}