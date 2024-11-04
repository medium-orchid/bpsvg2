package bpsvg2

import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode

data class Union(val first: DataType, val second: DataType): DataType {
    override fun put(builder: OutputBuilder, mode: OutputMode) {
        first.put(builder, mode)
        builder.append(" ", mode)
        second.put(builder, mode)
    }
}