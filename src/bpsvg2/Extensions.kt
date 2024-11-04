package bpsvg2

infix fun DataType.then(other: DataType): DataType {
    return Union(this, other)
}