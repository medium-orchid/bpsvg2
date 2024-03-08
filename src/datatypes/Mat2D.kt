package datatypes

data class Mat2D(val a: Double, val b: Double,
                 val c: Double, val d: Double,
                 val x: Double, val y: Double,
                 val unit: String?
    ) {

    companion object {
        val id = Mat2D(1.0, 0.0, 0.0, 1.0, 0.0, 0.0, null)

        fun offset(vec2: Vec2): Mat2D {
            return Mat2D(1.0, 0.0, 0.0, 1.0, vec2.x, vec2.y, vec2.unit)
        }

        private fun offsetUnits(unit: String?): String {
            return if (unit == null) {
                "unitless"
            } else {
                "'${unit}'"
            }
        }
    }

    private fun vectorGuard(other: Vec2) {
        if (this.hasOffset() && unit != other.unit) {
            throw IllegalArgumentException("Matrix has incompatible offset units" +
                    " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})")
        }
    }

    private fun matrixGuard(other: Mat2D) {
        if (this.hasOffset() && other.hasOffset() && this.unit != other.unit) {
            throw IllegalArgumentException("Matrices have incompatible offset units" +
                    " (${offsetUnits(this.unit)} and ${offsetUnits(other.unit)})")
        }
    }

    fun hasOffset(): Boolean {
        return x != 0.0 || y != 0.0
    }

    operator fun times(other: Mat2D): Mat2D {
        matrixGuard(other)
        return Mat2D(
            this.a * other.a + this.c * other.b,
            this.b * other.a + this.d * other.b,
            this.a * other.c + this.c * other.d,
            this.b * other.c + this.d * other.d,
            this.a * other.x + this.c * other.y + this.x,
            this.b * other.x + this.d * other.y + this.y,
            this.unit ?: other.unit
        )
    }

    operator fun times(other: Vec2): Vec2 {
        vectorGuard(other)
        return Vec2(a * other.x + c * other.y + x, b * other.x + d * other.y + y, other.unit)
    }

    operator fun plus(other: Vec2): Mat2D {
        vectorGuard(other)
        return Mat2D(a, b, c, d, x + other.x, y + other.y, other.unit)
    }
}