package bpsvg2.datatypes

import bpsvg2.SVGBuilder

interface DataType {
    fun put(builder: SVGBuilder)
    fun put(builder: SVGBuilder, cssMode: Boolean) {
        put(builder)
    }
}