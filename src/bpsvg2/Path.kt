package bpsvg2

import bpsvg2.eat.OutputMode
import bpsvg2.math.*
import bpsvg2.math.d2.*
import bpsvg2.geom.Curve

class Path(tag: String? = null) : Element(OutputMode.Path, tag) {

    var currentPoint: Vec2? = null
    var lastChild: Path? = null

    companion object {
        const val NO_CURRENT_POINT: String = "No current point is set"

        fun makeChild(parent: Element, tag: String? = null): Path {
            val child = Path(tag)
            parent.addChild(child)
            return child
        }
    }

    private fun getChild(tag: String, alternative: String = "<"): Path {
        val last = lastChild
        val lastTag = last?.backingTree?.name ?: ">"
        if (lastTag != tag && lastTag != alternative) {
            val child = makeChild(this, tag)
            lastChild = child
            return child
        } else {
            return makeChild(this, "")
        }
    }

    private fun addToCurrent(shift: Vec2) {
        val c = currentPoint ?: throw IllegalStateException(NO_CURRENT_POINT)
        currentPoint = c + shift
    }

    private fun accumulateRelative(points: Array<out Vec2>, step: Int) {
        var i = step - 1
        var c: Vec2 = currentPoint ?: throw IllegalStateException(NO_CURRENT_POINT)
        while (i < points.size) {
            c += points[i]
            i += step
        }
    }

    private fun addAsSecondaryAttribute(vararg arguments: Any) {
        for (i in arguments) {
            addAttribute("``" to i)
        }
    }

    fun moveTo(vararg v: Vec2) {
        val c = currentPoint
        // If currentPoint is null, we obviously have to move
        if (c == null) {
            val child = getChild("M")
            child.addAsSecondaryAttribute(*v)
        } else {
            var i = 0
            // We skip all the points that are at the current point
            while (i < v.size) {
                if (!v[i].approx(c)) break
                i++
            }
            // and if we have to move, we only move the remaining points
            // otherwise, we don't even emit an M element
            if (i < v.size) {
                val child = getChild("M")
                child.addAsSecondaryAttribute(*v.takeLast(v.size - i).toTypedArray())
            }
        }
        currentPoint = v.last()
    }

    fun moveBy(vararg v: Vec2) {
        var c = currentPoint
        if (c == null) {
            throw IllegalStateException(NO_CURRENT_POINT)
        } else {
            val child = getChild("m")
            for (i in v) {
                c += i
            }
            child.addAsSecondaryAttribute(*v)
            currentPoint = c
        }
    }

    fun lineTo(vararg v: Vec2) {
        val child = getChild("L", "M")
        currentPoint = v.last()
        child.addAsSecondaryAttribute(*v)
    }

    fun lineBy(vararg v: Vec2) {
        val child = getChild("l", "m")
        accumulateRelative(v, 1)
        child.addAsSecondaryAttribute(*v)
    }

    fun horizontalTo(vararg x: Dimension) {
        val child = getChild("H")
        val y = currentPoint?.y ?: throw IllegalStateException(NO_CURRENT_POINT)
        currentPoint = Vec2(x.last(), y)
        child.addAsSecondaryAttribute(*x)
    }

    fun horizontalBy(vararg x: Dimension) {
        val child = getChild("h")
        val c = currentPoint ?: throw IllegalStateException(NO_CURRENT_POINT)
        val y = c.y
        var xv = c.x
        for (i in x) {
            xv += i
        }
        currentPoint = Vec2(xv, y)
        child.addAsSecondaryAttribute(*x)
    }

    fun verticalTo(vararg y: Dimension) {
        val child = getChild("V")
        val x = currentPoint?.x ?: throw IllegalStateException(NO_CURRENT_POINT)
        currentPoint = Vec2(x, y.last())
        child.addAsSecondaryAttribute(*y)
    }

    fun verticalBy(vararg y: Dimension) {
        val child = getChild("v")
        val c = currentPoint ?: throw IllegalStateException(NO_CURRENT_POINT)
        val x = c.x
        var yv = c.y
        for (i in y) {
            yv += i
        }
        currentPoint = Vec2(x, yv)
        child.addAsSecondaryAttribute(*y)
    }

    fun fromCurve(c: Curve<Vec2>) {
        moveTo(c.points[0])
        val child = getChild(when(c.degree) {
            1 -> "L"
            2 -> "Q"
            3 -> "C"
            else -> throw IllegalArgumentException("cannot add degree ${c.degree} curve to path")
        })
        for (i in c.points.takeLast(c.degree)) {
            child.addAsSecondaryAttribute(i)
        }
        currentPoint = c.points.last()
    }

    fun fromCurves(c: Iterable<Curve<Vec2>>) {
        for (i in c) {
            fromCurve(i)
        }
    }

    fun cubicTo(startingControl: Vec2, endingControl: Vec2, endPoint: Vec2) {
        val child = getChild("C")
        currentPoint = endPoint
        child.addAsSecondaryAttribute(startingControl, endingControl, endPoint)
    }

    fun cubicBy(startingControl: Vec2, endingControl: Vec2, endShift: Vec2) {
        val child = getChild("c")
        addToCurrent(endShift)
        child.addAsSecondaryAttribute(startingControl, endingControl, endShift)
    }

    fun smoothCubicTo(endingControl: Vec2, endPoint: Vec2) {
        val child = getChild("S")
        currentPoint = endPoint
        child.addAsSecondaryAttribute(endingControl, endPoint)
    }

    fun smoothCubicBy(endingControl: Vec2, endShift: Vec2) {
        val child = getChild("s")
        addToCurrent(endShift)
        child.addAsSecondaryAttribute(endingControl, endShift)
    }

    fun quadraticTo(control: Vec2, endPoint: Vec2) {
        val child = getChild("Q")
        currentPoint = endPoint
        child.addAsSecondaryAttribute(control, endPoint)
    }

    fun quadraticBy(control: Vec2, endShift: Vec2) {
        val child = getChild("q")
        addToCurrent(endShift)
        child.addAsSecondaryAttribute(control, endShift)
    }

    fun smoothQuadraticTo(control: Vec2, endPoint: Vec2) {
        val child = getChild("T")
        currentPoint = endPoint
        child.addAsSecondaryAttribute(control, endPoint)
    }

    fun smoothQuadraticBy(control: Vec2, endShift: Vec2) {
        val child = getChild("t")
        addToCurrent(endShift)
        child.addAsSecondaryAttribute(control, endShift)
    }

    fun arcTo(rx: Any, ry: Any, angle: Angle, largeArc: Boolean, clockwise: Boolean, endPoint: Vec2) {
        val child = getChild("A")
        currentPoint = endPoint
        child.addAsSecondaryAttribute(rx, ry, angle, largeArc.flag(), clockwise.flag(), endPoint)
    }

    fun arcBy(rx: Any, ry: Any, angle: Angle, largeArc: Boolean, clockwise: Boolean, endShift: Vec2) {
        val child = getChild("a")
        addToCurrent(endShift)
        child.addAsSecondaryAttribute(rx, ry, angle, largeArc.flag(), clockwise.flag(), endShift)
    }

    fun close() {
        getChild("z")
    }

    operator fun invoke(vararg attributes: Attribute, operation: PathOperation? = null): Path {
        for (i in attributes) {
            addAttributes(i)
        }
        if (operation != null) this.operation()
        return this
    }
}