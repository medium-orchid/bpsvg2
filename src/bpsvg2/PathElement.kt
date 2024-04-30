package bpsvg2

import bpsvg2.datatypes.math2d.Vec2
import bpsvg2.datatypes.flag

typealias PathOperation = PathElement.() -> Unit

class PathElement(root: SVG? = null) : SVGElement("path", root) {

    class Name(private val parent: SVGElement) {
        operator fun invoke(vararg attributes: Attribute, operation: PathOperation? = null): PathElement {
            val element = PathElement()(*attributes, operation = operation)
            parent.addChild(element)
            return element
        }
    }

    operator fun invoke(vararg attributes: Attribute, operation: PathOperation? = null): PathElement {
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

    fun horizontalTo(vararg x: Any) {
        d.add("H" to x.asList())
    }

    fun horizontalBy(vararg x: Any) {
        d.add("h" to x.asList())
    }

    fun verticalTo(vararg y: Any) {
        d.add("V" to y.asList())
    }

    fun verticalBy(vararg y: Any) {
        d.add("v" to y.asList())
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

    fun arcTo(rx: Any, ry: Any, angle: Any, largeArc: Boolean, clockwise: Boolean, endPoint: Vec2) {
        d.add("A" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endPoint))
    }

    fun arcBy(rx: Any, ry: Any, angle: Any, largeArc: Boolean, clockwise: Boolean, endShift: Vec2) {
        d.add("a" to listOf(rx, ry, angle, largeArc.flag(), clockwise.flag(), endShift))
    }

    fun close() {
        d.add("z" to listOf())
    }

    override fun build(svg: OutputBuilder) {
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