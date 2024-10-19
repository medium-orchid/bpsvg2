package bpsvg2.geom.discrete

import bpsvg2.math.d3.*
import bpsvg2.math.*

class Polyhedron(val vertices: Array<Vec3>) {

    constructor(vertices: Array<Vec3>, vararg faces: IntArray) : this(vertices) {
        for (i in faces) {
            addFace(*i)
        }
    }

    val nextVertex = HashMap<Edge, Int>()
    val prevVertex = HashMap<Edge, Int>()

    fun next(edge: Edge): Edge {
        val n = nextVertex[edge] ?: throw IllegalArgumentException("$edge has no next")
        return edge.second to n
    }

    fun prev(edge: Edge): Edge {
        val p = prevVertex[edge] ?: throw IllegalArgumentException("$edge has no prev")
        return edge.second to p
    }

    fun twin(edge: Edge): Edge {
        return edge.second to edge.first
    }

    fun line(edge: Edge): Line<Vec3> {
        return Line(vertices[edge.first], vertices[edge.second])
    }

    fun addEdges(vararg vertexIndices: Int) {
        for (i in 0..<vertexIndices.size - 2) {
            val a = vertexIndices[i]
            val b = vertexIndices[i + 1]
            val c = vertexIndices[i + 2]
            nextVertex[a to b] = c
            prevVertex[b to c] = a
        }
    }

    fun addFace(vararg vertexIndices: Int) {
        val n = vertexIndices.size
        for (i in vertexIndices.indices) {
            val a = vertexIndices[i]
            val b = vertexIndices[(i + 1) % n]
            val c = vertexIndices[(i + 2) % n]
            nextVertex[a to b] = c
            prevVertex[b to c] = a
        }
    }

    val faces: Iterator<Polygon<Vec3>> get() {
        return iterator {
            val taken = HashSet<Edge>()
            for (i in nextVertex.keys) {
                if (i in taken) continue
                val poly = mutableListOf<Vec3>()
                var c = i
                do {
                    poly.add(vertices[c.first])
                    taken.add(c)
                    c = next(c)
                    yield(Polygon(poly))
                } while (c != i)
            }
        }
    }

    fun volume(): Dimension {
        var v = zero
        for (f in faces) {
            val c = f.centroid()
            for (i in f) {
                val n = (i.x0 - c).cross(i.x1 - c)
                v += i.x0.dot(n)
            }
        }
        return v / 6
    }

    val doubledEdges: Iterator<Line<Vec3>> get() {
        return iterator {
            val taken = HashSet<Edge>()
            for (i in nextVertex.keys) {
                if (i.first < i.second) yield(line(i))
            }
        }
    }
}