package bpsvg2.math.d3

import bpsvg2.DataType
import bpsvg2.eat.OutputBuilder
import bpsvg2.math.*
import bpsvg2.eat.OutputMode
import bpsvg2.math.d2.Angle
import kotlin.math.pow
import kotlin.math.sqrt

data class Vec3(val x: Dimension, val y: Dimension, val z: Dimension) : DataType, Vector<Vec3> {

    constructor(x: Double, y: Double, z: Double) : this(x.d, y.d, z.d)

    companion object {
        val zero = Vec3(0.0, 0.0, 0.0)
        val X = Vec3(1.0, 0.0, 0.0)
        val Y = Vec3(0.0, 1.0, 0.0)
        val Z = Vec3(0.0, 0.0, 1.0)

        fun randomUnit(): Vec3 {
            return Vec3(randomNormal(), randomNormal(), randomNormal()).normalized()
        }
    }

    fun approximatelyEquals(other: Vec3): Boolean {
        return approx(x, other.x) && approx(y, other.y) && approx(z, other.z)
    }

    fun convert(unitX: CSSUnits, unitY: CSSUnits, unitZ: CSSUnits): Vec3 {
        return Vec3(x.convert(unitX), y.convert(unitY), z.convert(unitZ))
    }

    override operator fun plus(other: Vec3): Vec3 {
        return Vec3(this.x + other.x, this.y + other.y, this.z + other.z)
    }

    override operator fun minus(other: Vec3): Vec3 {
        return Vec3(this.x - other.x, this.y - other.y, this.z - other.z)
    }

    override fun norm(): Dimension {
        val unit = Dimension.commonUnit(x, y, z)
        return Dimension(sqrt(
            x.convertValue(unit).pow(2)
                    + y.convertValue(unit).pow(2))
                    + z.convertValue(unit).pow(2),
            unit)
    }

    fun normalized(): Vec3 {
        return this / norm()
    }

    override operator fun times(other: Double): Vec3 {
        return Vec3(x * other, y * other, z * other)
    }

    operator fun times(other: Dimension): Vec3 {
        return Vec3(x * other, y * other, z * other)
    }

    override operator fun div(other: Double): Vec3 {
        return Vec3(x / other, y / other, z / other)
    }

    operator fun div(other: Dimension): Vec3 {
        return Vec3(x / other, y / other, z / other)
    }

    fun dot(other: Vec3): Dimension {
        return x * other.x + y * other.y + z * other.z
    }

    fun cross(other: Vec3): Vec3 {
        return Vec3(y * other.z - z * other.y, z * other.x - x * other.z, x * other.y - y * other.x)
    }

    override fun toString(): String {
        return "Vec3($x, $y, $z)"
    }

    fun toTrans(): Trans3D {
        return Trans3D(1.0, Quat.id, this)
    }

    fun toMat3D(): Mat3D {
        val zero = bpsvg2.math.zero
        return Mat3D(
            one, zero, zero, x,
            zero, one, zero, y,
            zero, zero, one, z,
            zero, zero, zero, one
        )
    }

    fun rotate(axis: Vec3, angle: Angle): Vec3 {
        val cos = angle.cos()
        return cos * this + angle.sin() * axis.cross(this) + (1 - cos) * axis.dot(this) * axis
    }

    fun axisAngle(angle: Angle): Trans3D {
        return Trans3D(1.0, Quat.fromAxisAngle(this, angle), zero)
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        builder.join(mode, x, y, z)
    }
}