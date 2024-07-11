package bpsvg2.math

class UnitConverter {

    companion object {
        const val EPS = 1E-10
    }

    private val conversion = hashMapOf<Pair<U, U>, Double>()

    private fun addConversion(from: U, to: U, factor: Double) {
        conversion[from to to] = factor
        conversion[to to from] = 1 / factor
        fillConversions(from, to)
        fillConversions(to, from)
    }

    private fun fillConversions(from: U, to: U) {
        for (i in U.entries) {
            if ((i to to !in conversion) && (i to from in conversion)) {
                conversion[i to to] = conversion[i to from]!! * conversion[from to to]!!
            }
            if ((from to i !in conversion) && (to to i in conversion)) {
                conversion[from to i] = conversion[from to to]!! * conversion[to to i]!!
            }
        }
    }

    fun convertValue(dim: Dimension, unit: U): Double {
        if (unit == dim.unit) return dim.value
        if (dim.value < EPS) return 0.0
        val factor = conversion[dim.unit to unit]
            ?: throw IllegalArgumentException("cannot convert $dim to ${Dimension.niceName(unit)}")
        return factor * dim.value
    }

    fun convert(dim: Dimension, unit: U): Dimension {
        if (unit == dim.unit) return dim
        return Dimension(convertValue(dim, unit), unit)
    }

    init {
        addConversion(U.PX, U.CM, 96 / 2.54)
        addConversion(U.MM, U.CM, 10.0)
        addConversion(U.Q, U.CM, 40.0)
        addConversion(U.PX, U.IN, 96.0)
        addConversion(U.PC, U.PT, 12.0)
        addConversion(U.IN, U.PT, 72.0)

        addConversion(U.MS, U.S, 1000.0)

        addConversion(U.KHZ, U.HZ, 1000.0)

        addConversion(U.DPCM, U.DPI, 2.54)
        addConversion(U.DPI, U.DPPX, 96.0)
    }
}