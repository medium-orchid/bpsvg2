package bpsvg2.math

import kotlin.math.absoluteValue
import kotlin.math.pow

class UnitConverter {

    companion object {
        const val EPS = 1E-10
    }

    private val conversion = hashMapOf<Pair<CSSUnits, CSSUnits>, Double>()

    private fun addConversion(from: CSSUnits, to: CSSUnits, factor: Double) {
        conversion[from to to] = factor
        conversion[to to from] = 1 / factor
        fillConversions(from, to)
        fillConversions(to, from)
    }

    private fun fillConversions(from: CSSUnits, to: CSSUnits) {
        for (i in CSSUnits.entries) {
            if ((i to to !in conversion) && (i to from in conversion)) {
                conversion[i to to] = conversion[i to from]!! * conversion[from to to]!!
            }
            if ((from to i !in conversion) && (to to i in conversion)) {
                conversion[from to i] = conversion[from to to]!! * conversion[to to i]!!
            }
        }
    }

    fun convertValue(dim: Dimension, unit: CSSUnits): Double {
        if (unit == dim.unit) return dim.value
        if (dim.value.absoluteValue < EPS) return 0.0
        val factor = conversion[dim.unit to unit]
            ?: throw IllegalArgumentException("cannot convert $dim to ${Dimension.niceName(unit)}")
        return factor.pow(dim.exp) * dim.value
    }

    fun convert(dim: Dimension, unit: CSSUnits): Dimension {
        if (unit == dim.unit) return dim
        return Dimension(convertValue(dim, unit), unit, dim.exp)
    }

    init {
        addConversion(CSSUnits.PX, CSSUnits.CM, 96 / 2.54)
        addConversion(CSSUnits.MM, CSSUnits.CM, 10.0)
        addConversion(CSSUnits.Q, CSSUnits.CM, 40.0)
        addConversion(CSSUnits.PX, CSSUnits.IN, 96.0)
        addConversion(CSSUnits.PC, CSSUnits.PT, 12.0)
        addConversion(CSSUnits.IN, CSSUnits.PT, 72.0)

        addConversion(CSSUnits.MS, CSSUnits.S, 1000.0)

        addConversion(CSSUnits.KHZ, CSSUnits.HZ, 1000.0)

        addConversion(CSSUnits.DPCM, CSSUnits.DPI, 2.54)
        addConversion(CSSUnits.DPI, CSSUnits.DPPX, 96.0)
    }
}