package bpsvg2.geom.discrete

import bpsvg2.math.d3.*
import bpsvg2.math.*

class Polyhedron() {

    companion object {
        val tetrahedron = Polyhedron(
            arrayOf(
                Vec3(-1, -1,  1),
                Vec3(-1,  1, -1),
                Vec3( 1, -1, -1),
                Vec3( 1,  1,  1),
            ),
            intArrayOf(0, 1, 2),
            intArrayOf(2, 1, 3),
            intArrayOf(3, 0, 2),
            intArrayOf(3, 1, 0),
        )
        val octahedron = Polyhedron(
            arrayOf(
                Vec3(0, 0, -1),
                Vec3(-1, 0, 0),
                Vec3(0, 1, 0),
                Vec3(0, -1, 0),
                Vec3(0, 0, 1),
                Vec3(1, 0, 0),
            ),
            intArrayOf(0, 1, 2),
            intArrayOf(3, 4, 1),
            intArrayOf(2, 5, 0),
            intArrayOf(4, 5, 2),
            intArrayOf(3, 5, 4),
            intArrayOf(1, 4, 2),
            intArrayOf(3, 1, 0),
            intArrayOf(0, 5, 3),
        )
    }

    constructor(vertices: Array<Vec3>, vararg faces: IntArray): this() {
        this.vertices.addAll(vertices)
        for (i in faces) {
            addFace(*i)
        }
    }

    constructor(vertices: Array<Vec3>, nextVertex: Map<Edge, Int>, prevVertex: Map<Edge, Int>): this() {
        this.vertices.addAll(vertices)
        this.nextVertex.putAll(nextVertex)
        this.prevVertex.putAll(prevVertex)
    }

    val vertices = mutableListOf<Vec3>()

    val nextVertex = HashMap<Edge, Int>()
    val prevVertex = HashMap<Edge, Int>()

    val edges get() = nextVertex.keys.union(prevVertex.keys)
    val lines get() = edges.map { e -> Line(vertices[e.first], vertices[e.second])}

    operator fun plus(other: Vec3): Polyhedron {
        return Polyhedron(vertices.map { x -> x + other }.toTypedArray(), nextVertex, prevVertex)
    }

    operator fun minus(other: Vec3): Polyhedron {
        return Polyhedron(vertices.map { x -> x - other }.toTypedArray(), nextVertex, prevVertex)
    }

    operator fun times(other: Dimension): Polyhedron {
        return Polyhedron(vertices.map { x -> x * other }.toTypedArray(), nextVertex, prevVertex)
    }

    operator fun div(other: Dimension): Polyhedron {
        return Polyhedron(vertices.map { x -> x / other }.toTypedArray(), nextVertex, prevVertex)
    }

    operator fun times(other: Double): Polyhedron {
        return Polyhedron(vertices.map { x -> x * other }.toTypedArray(), nextVertex, prevVertex)
    }

    operator fun div(other: Double): Polyhedron {
        return Polyhedron(vertices.map { x -> x / other }.toTypedArray(), nextVertex, prevVertex)
    }

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

    fun centroid(): Vec3 {
        var n = vertices[0].zero()
        var d = 0.d
        for (i in lines) {
            val norm = i.norm()
            n += norm * i.midpoint()
            d += norm
        }
        return n / d
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