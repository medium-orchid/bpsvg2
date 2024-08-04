package bpsvg2.math

interface Vector<V> {
    operator fun plus(other: V): V
    operator fun minus(other: V): V
    operator fun times(other: Double): V
    operator fun div(other: Double): V
    fun norm(): Dimension
}