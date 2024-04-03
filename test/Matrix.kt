import bpsvg2.datatypes.Mat2D
import kotlin.random.Random

fun main() {
    for (i in 0..<100) {
        val m = randomMatrix()
        assert(Mat2D.id.approximatelyEquals(
            m * m.inverse()
        ))
        assert(Mat2D.id.approximatelyEquals(
            m.pow(3) * m.pow(-3)
        ))
        assert(m.pow(3).approximatelyEquals(
            m * m * m
        ))
        assert(Mat2D.id.approximatelyEquals(
            Mat2D.reflect(Random.nextDouble(0.0, 360.0)).pow(2)
        ))
    }
    assert(Mat2D.id.approximatelyEquals(
        Mat2D.rotate(360 * 8.0 / 9.0).pow(9)
    ))
}

fun randomMatrix(): Mat2D {
    return Mat2D(entry(), entry(), entry(), entry(), entry(), entry())
}

fun entry(): Double {
    return Random.nextDouble(-2.0, 2.0)
}

fun assert(value: Boolean) {
    if (!value) throw Exception()
}