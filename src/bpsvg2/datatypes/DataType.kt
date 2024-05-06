package bpsvg2.datatypes

import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode

interface DataType {
    fun put(builder: OutputBuilder, mode: OutputMode)
}