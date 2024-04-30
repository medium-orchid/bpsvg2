package bpsvg2.datatypes

import bpsvg2.OutputBuilder

interface DataType {
    fun put(builder: OutputBuilder)
}