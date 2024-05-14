package bpsvg2

import bpsvg2.eat.ElementAttributeTree
import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode
import bpsvg2.math.Length
import bpsvg2.math.Rect
import bpsvg2.math.d2.*
import bpsvg2.math.d3.*
import java.io.File

open class Element(private val backingTree: ElementAttributeTree) : DataType {

    constructor(mode: OutputMode, tag: String? = null, root: Boolean = false) :
            this(ElementAttributeTree(mode, tag, root))

    fun addAttributes(vararg attributes: Attribute) {
        for (i in attributes) {
            addAttribute(i)
        }
    }

    fun addChild(element: Element) {
        backingTree.children.add(element.backingTree)
    }

    fun string(content: Any) {
        backingTree.children.add(ElementAttributeTree(OutputMode.Text, content.toString()))
    }

    fun addAttribute(attribute: Attribute, first: Boolean = false, forceAdd: Boolean = false) {
        val f = attribute.first
        val s = attribute.second
        if (!forceAdd) {
            when (s) {
                is Mat2D -> if (s.approximatelyEquals(Mat2D.id)) return
                is Mat3D -> if (s.approximatelyEquals(Mat3D.id)) return
                is Quat -> if (s.approximatelyEquals(Quat.id)) return
                is Ortho2D -> if (s.approximatelyEquals(Ortho2D.id)) return
                is Ortho3D -> if (s.approximatelyEquals(Ortho3D.id)) return
            }
        }
        if (f.startsWith("**")) {
            val suffix = f.substring(2)
            when (s) {
                is Length -> {
                    addAttribute("${suffix}width" to s, first, forceAdd)
                    addAttribute("${suffix}height" to s, first, forceAdd)
                }
                is Vec2 -> {
                    val u = s.unit ?: ""
                    addAttribute("${suffix}width" to "${s.x}$u", first, forceAdd)
                    addAttribute("${suffix}height" to "${s.y}$u", first, forceAdd)
                }
            }
        } else if (f.startsWith("*")) {
            val suffix = f.substring(1)
            when (s) {
                is Length -> {
                    addAttribute("${suffix}x" to s, first, forceAdd)
                    addAttribute("${suffix}y" to s, first, forceAdd)
                }
                is Vec2 -> {
                    val u = s.unit ?: ""
                    addAttribute("${suffix}x" to "${s.x}$u", first, forceAdd)
                    addAttribute("${suffix}y" to "${s.y}$u", first, forceAdd)
                }
                is Rect -> {
                    val u = s.topLeft.unit ?: ""
                    addAttribute("${suffix}x" to "${s.topLeft.x}$u", first, forceAdd)
                    addAttribute("${suffix}y" to "${s.topLeft.y}$u", first, forceAdd)
                    addAttribute("${suffix}width" to s.width, first, forceAdd)
                    addAttribute("${suffix}height" to s.height, first, forceAdd)
                }
            }
        } else {
            addRawAttribute(f to s, first)
        }
    }

    private fun addRawAttribute(attribute: Attribute, first: Boolean) {
        val f = attribute.first
        val s = attribute.second
        if (first) {
            backingTree.attributes.add(0, f to s)
        } else {
            backingTree.attributes.add(f to s)
        }
    }

    fun childTree(mode: OutputMode? = null): ElementAttributeTree {
        return ElementAttributeTree(mode ?: backingTree.mode)
    }

    fun printTree() {
        backingTree.print()
    }

    override fun put(builder: OutputBuilder, mode: OutputMode) {
        backingTree.put(builder, mode)
    }

    fun saveTo(file: String, builder: OutputBuilder? = null) {
        val out = builder ?: OutputBuilder()
        put(out, backingTree.mode)
        File(file).writeText(out.toString())
    }
}