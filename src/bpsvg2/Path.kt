package bpsvg2

import bpsvg2.eat.OutputMode
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.math.geom.Curve

class Path(tag: String? = null) : Element(OutputMode.Path, tag) {

    companion object {
        fun makeChild(parent: Element, tag: String? = null): Path {
            val child = Path(tag)
            parent.addChild(child)
            return child
        }
    }

    private fun addAsSecondaryAttribute(vararg arguments: Any) {
        for (i in arguments) {
            addAttribute("``" to i)
        }
    }

    fun moveTo(vararg v: Vec2) {
        val child = makeChild(this, "M")
        child.addAsSecondaryAttribute(*v)
    }

    fun moveBy(vararg v: Vec2) {
        val child = makeChild(this, "m")
        child.addAsSecondaryAttribute(*v)
    }

    fun lineTo(vararg v: Vec2) {
        val child = makeChild(this, "L")
        child.addAsSecondaryAttribute(*v)
    }

    fun lineBy(vararg v: Vec2) {
        val child = makeChild(this, "l")
        child.addAsSecondaryAttribute(*v)
    }

    fun horizontalTo(vararg x: Any) {
        val child = makeChild(this, "H")
        child.addAsSecondaryAttribute(*x)
    }

    fun horizontalBy(vararg x: Any) {
        val child = makeChild(this, "h")
        child.addAsSecondaryAttribute(*x)
    }

    fun verticalTo(vararg y: Any) {
        val child = makeChild(this, "V")
        child.addAsSecondaryAttribute(*y)
    }

    fun verticalBy(vararg y: Any) {
        val child = makeChild(this, "v")
        child.addAsSecondaryAttribute(*y)
    }

    fun fromCurve(c: Curve<Vec2>) {
        moveTo(c.points[0])
        val child = makeChild(this, when(c.degree) {
            1 -> "L"
            2 -> "Q"
            3 -> "C"
            else -> throw IllegalArgumentException("cannot add degree ${c.degree} curve to path")
        })
        for (i in c.points.takeLast(c.degree)) {
            child.addAsSecondaryAttribute(i)
        }
    }

    fun cubicTo(startingControl: Vec2, endingControl: Vec2, endPoint: Vec2) {
        val child = makeChild(this, "C")
        child.addAsSecondaryAttribute(startingControl, endingControl, endPoint)
    }

    fun cubicBy(startingControl: Vec2, endingControl: Vec2, endShift: Vec2) {
        val child = makeChild(this, "c")
        child.addAsSecondaryAttribute(startingControl, endingControl, endShift)
    }

    fun smoothCubicTo(endingControl: Vec2, endPoint: Vec2) {
        val child = makeChild(this, "S")
        child.addAsSecondaryAttribute(endingControl, endPoint)
    }

    fun smoothCubicBy(endingControl: Vec2, endShift: Vec2) {
        val child = makeChild(this, "s")
        child.addAsSecondaryAttribute(endingControl, endShift)
    }

    fun quadraticTo(control: Vec2, endPoint: Vec2) {
        val child = makeChild(this, "Q")
        child.addAsSecondaryAttribute(control, endPoint)
    }

    fun quadraticBy(control: Vec2, endShift: Vec2) {
        val child = makeChild(this, "q")
        child.addAsSecondaryAttribute(control, endShift)
    }

    fun smoothQuadraticTo(control: Vec2, endPoint: Vec2) {
        val child = makeChild(this, "T")
        child.addAsSecondaryAttribute(control, endPoint)
    }

    fun smoothQuadraticBy(control: Vec2, endShift: Vec2) {
        val child = makeChild(this, "t")
        child.addAsSecondaryAttribute(control, endShift)
    }

    fun arcTo(rx: Any, ry: Any, angle: Angle, largeArc: Boolean, clockwise: Boolean, endPoint: Vec2) {
        val child = makeChild(this, "A")
        child.addAsSecondaryAttribute(rx, ry, angle, largeArc.flag(), clockwise.flag(), endPoint)
    }

    fun arcBy(rx: Any, ry: Any, angle: Angle, largeArc: Boolean, clockwise: Boolean, endShift: Vec2) {
        val child = makeChild(this, "a")
        child.addAsSecondaryAttribute(rx, ry, angle, largeArc.flag(), clockwise.flag(), endShift)
    }

    fun close() {
        makeChild(this, "z")
    }

    operator fun invoke(vararg attributes: Attribute, operation: PathOperation? = null): Path {
        for (i in attributes) {
            addAttributes(i)
        }
        if (operation != null) this.operation()
        return this
    }
}