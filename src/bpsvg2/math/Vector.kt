package bpsvg2.math

interface Vector<V> {
    operator fun plus(other: V): V
    operator fun minus(other: V): V
    operator fun times(other: Double): V
    operator fun div(other: Double): V
    operator fun times(other: Dimension): V
    operator fun div(other: Dimension): V
    fun norm(): Dimension
    fun zero(): V
}