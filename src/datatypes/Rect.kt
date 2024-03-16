package datatypes

import SVGBuilder

data class Rect(val topLeft: Vec2, val width: Length, val height: Length): DataType {

    constructor(topLeft: Vec2, width: Double, height: Double): this(topLeft, width.length, height.length)

    constructor(topLeft: Vec2, width: Int, height: Int): this(topLeft, width.length, height.length)

    override fun put(builder: SVGBuilder) {
        topLeft.put(builder)
        builder.append(',')
        width.put(builder)
        builder.append(',')
        height.put(builder)
    }

    fun percent(vec: Vec2): Vec2 {
        if (vec.unit != "%") throw IllegalArgumentException("Vector $vec is not a percent vector")
        return topLeft + Vec2(width * vec.x / 100.0, height * vec.y / 100.0)
    }
}