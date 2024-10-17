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
}