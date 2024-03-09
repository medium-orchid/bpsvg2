import datatypes.*

typealias PathOperation = PathElement.() -> Unit

class PathElement(root: SVGRoot? = null): SVGElement("path", root) {

    class PathSVGElement(private val parent: SVGElement) {
        operator fun invoke(vararg attributes: Pair<String, Any>, operation: PathOperation? = null): PathElement {
            val element = PathElement() (*attributes, operation = operation)
            parent.addChild(element)
            return element
        }
    }

    operator fun invoke(vararg attributes: Pair<String, Any>, operation: PathOperation? = null): PathElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) {
            this.operation()
        }
        return this
    }

    private val d = arrayListOf<Pair<String, List<Any>>>()

    fun moveTo(vararg v: Vec2) {
        d.add("M" to v.asList())
    }

    fun moveBy(vararg v: Vec2) {
        d.add("m" to v.asList())
    }

    fun lineTo(vararg v: Vec2) {
        d.add("L" to v.asList())
    }

    fun lineBy(vararg v: Vec2) {
        d.add("l" to v.asList())
    }

    fun horizontalTo(vararg x: Double) {
        d.add("H" to x.asList())
    }

    fun horizontalTo(vararg x: Length) {
        d.add("H" to x.asList())
    }

    fun horizontalBy(vararg x: Double) {
        d.add("h" to x.asList())
    }

    fun horizontalBy(vararg x: Length) {
        d.add("h" to x.asList())
    }

    fun verticalTo(vararg y: Double) {
        d.add("H" to y.asList())
    }

    fun verticalTo(vararg y: Length) {
        d.add("H" to y.asList())
    }

    fun verticalBy(vararg y: Double) {
        d.add("h" to y.asList())
    }

    fun verticalBy(vararg y: Length) {
        d.add("h" to y.asList())
    }

    fun cubicTo(startingControl: Vec2, endingControl: Vec2, endPoint: Vec2) {
        d.add("C" to listOf(startingControl, endingControl, endPoint))
    }

    fun cubicBy(startingControl: Vec2, endingControl: Vec2, endShift: Vec2) {
        d.add("c" to listOf(startingControl, endingControl, endShift))
    }

    fun smoothCubicTo(endingControl: Vec2, endPoint: Vec2) {
        d.add("S" to listOf(endingControl, endPoint))
    }

    fun smoothCubicBy(endingControl: Vec2, endShift: Vec2) {
        d.add("s" to listOf(endingControl, endShift))
    }

    fun quadraticTo(control: Vec2, endPoint: Vec2) {
        d.add("Q" to listOf(control, endPoint))
    }

    fun quadraticBy(control: Vec2, endShift: Vec2) {
        d.add("q" to listOf(control, endShift))
    }

    fun smoothQuadraticTo(control: Vec2, endPoint: Vec2) {
        d.add("T" to listOf(control, endPoint))
    }

    fun smoothQuadraticBy(control: Vec2, endShift: Vec2) {
        d.add("t" to listOf(control, endShift))
    }

    fun arcTo(rx: Double, ry: Double, angle: Double, largeArc: Boolean, clockwise: Boolean, endPoint: Vec2) {
        d.add("A" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endPoint))
    }

    fun arcTo(rx: Length, ry: Length, angle: Double, largeArc: Boolean, clockwise: Boolean, endPoint: Vec2) {
        d.add("A" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endPoint))
    }

    fun arcBy(rx: Double, ry: Double, angle: Double, largeArc: Boolean, clockwise: Boolean, endShift: Vec2) {
        d.add("a" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endShift))
    }

    fun arcBy(rx: Length, ry: Length, angle: Double, largeArc: Boolean, clockwise: Boolean, endShift: Vec2) {
        d.add("a" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endShift))
    }

    fun close() {
        d.add("z" to listOf())
    }

    override fun build(svg: SVGBuilder) {
        buildInitial(svg)
        svg.append(" d=\"")
        if (svg.indentPath) svg.indent()
        var spaceNeeded = false
        for ((i, l) in d) {
            if (svg.indentPath) {
                svg.newline()
            } else if (spaceNeeded) {
                svg.append(" ")
            } else {
                spaceNeeded = true
            }
            svg.append(i)
            for (j in l) {
                svg.withSpaceBefore(j)
            }
        }
        if (svg.indentPath) svg.unindent()
        svg.append("\"")
        if (svg.indentPath) svg.newline()
        buildFinal(svg)
    }
}