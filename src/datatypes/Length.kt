package datatypes

import SVGBuilder

data class Length(val l: Double, val unit: String? = null): DataType {

    init {
        if (unit == "") throw IllegalArgumentException("Unitless lengths should have null unit")
    }

    override fun put(builder: SVGBuilder) {
        builder.append(l)
        builder.append(unit ?: "")
    }
}