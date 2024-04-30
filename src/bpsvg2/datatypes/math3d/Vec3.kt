package bpsvg2.datatypes.math3d

import bpsvg2.OutputBuilder
import bpsvg2.datatypes.*
import kotlin.math.sqrt

data class Vec3(val x: Double, val y: Double, val z: Double, val unit: String? = null) : DataType {

    constructor(x: Int, y: Int, z: Int, unit: String? = null) : this(x.toDouble(), y.toDouble(), z.toDouble(), unit)

    constructor(x: Length, y: Length, z: Length) : this(x.l, y.l, z.l, x.unit) {
        if (x.unit != y.unit || x.unit != z.unit) {
            throw IllegalArgumentException("$x and $y do not have matching units")
        }
    }

    companion object {
        val zero = Vec3(0, 0, 0)
        val X = Vec3(1, 0, 0)
        val Y = Vec3(0, 1, 0)
        val Z = Vec3(0, 0, 1)

        fun randomUnit(): Vec3 {
            return Vec3(randomNormal(), randomNormal(), randomNormal()).normalized()
        }
    }

    init {
        if (unit == "") throw IllegalArgumentException("Unitless vectors should have null unit")
    }

    fun approximatelyEquals(other: Vec3): Boolean {
        return unit == other.unit && approx(x, other.x) && approx(y, other.y) && approx(z, other.z)
    }

    fun u(unit: String? = null): Vec3 {
        return Vec3(x, y, z, unit)
    }

    private fun guard(other: Vec3) {
        if (this.unit != other.unit) {
            throw IllegalArgumentException("$this and $other have different units and are not compatible")
        }
    }

    operator fun plus(other: Vec3): Vec3 {
        guard(other)
        return Vec3(this.x + other.x, this.y + other.y, this.z + other.z, unit)
    }

    operator fun minus(other: Vec3): Vec3 {
        guard(other)
        return Vec3(this.x - other.x, this.y - other.y, this.z - other.z, unit)
    }

    fun normSquared(): Double {
        return x * x + y * y + z * z
    }

    fun norm(): Double {
        return sqrt(normSquared())
    }

    fun normalized(): Vec3 {
        return this / norm()
    }

    operator fun times(other: Double): Vec3 {
        return Vec3(x * other, y * other, z * other, unit)
    }

    operator fun div(other: Double): Vec3 {
        return Vec3(x / other, y / other, z / other, unit)
    }

    operator fun div(other: Int): Vec3 {
        return this / other.toDouble()
    }

    fun dot(other: Vec3): Double {
        return x * other.x + y * other.y + z * other.z
    }

    fun cross(other: Vec3): Vec3 {
        return Vec3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)
    }

    override fun toString(): String {
        return "Vec3($x, $y, $z)${unit ?: ""}"
    }

    fun toOrtho(): Ortho3D {
        return Ortho3D(1.0, Quat.id, this)
    }

    fun axisAngle(angle: Angle): Ortho3D {
        return Ortho3D(1.0, Quat.fromAxisAngle(this, angle), zero)
    }

    override fun put(builder: OutputBuilder) {
        builder.append(x)
        builder.withComma(unit ?: "")
        builder.append(y)
        builder.withComma(unit ?: "")
        builder.append(z)
        builder.append(unit ?: "")
    }
}