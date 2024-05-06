package bpsvg2.eat

import bpsvg2.Attribute
import bpsvg2.OutputBuilder
import bpsvg2.datatypes.DataType

class ElementAttributeTree(val mode: EATMode, val name: String? = null): DataType {
    val attributes = arrayListOf<Attribute>()
    val children = arrayListOf<ElementAttributeTree>()

    override fun put(builder: OutputBuilder) {
        when (mode) {
            EATMode.XML -> putXML(builder)
            EATMode.CSS -> putCSS(builder)
            EATMode.Path -> putPath(builder)
            EATMode.Text -> if (name != null) builder.append(name)
        }
    }

    fun putXML(builder: OutputBuilder) {
        if (name != null) {
            builder.newline().append("<$name")
            for (i in attributes) {
                builder.append(" ${i.first}=\"").append(i.second).append("\"")
            }
            if (children.isNotEmpty()) {
                builder.append(">")
                builder.indent()
            }
        }
        for (child in children) {
            val cData = builder.cDataStrict && child.mode != EATMode.XML
            if (cData) {
                builder.newline().append("<![CDATA[").indent()
            }
            child.put(builder)
            if (cData) {
                builder.unindent().newline().append("]]>")
            }
        }
        if (name != null) {
            if (children.isEmpty()) {
                builder.append(" />").newline()
            } else {
                builder.unindent()
                builder.newline().append("</$name>")
            }
        }
    }

    fun putCSS(builder: OutputBuilder) {
        val empty = attributes.isEmpty() && children.isEmpty()
        if (name != null) {
            builder.newline().append("$name")
            if (empty) {
                builder.append(" {").indent()
            } else {
                builder.append(";")
            }
        }
        for (i in attributes) {
            builder.newline().append("${i.first}: ").append(i.second).append(";")
        }
        for (child in children) {
            child.put(builder)
        }
        if (name != null && !empty) {
            builder.unindent().newline().append("}")
        }
    }

    fun putPath(builder: OutputBuilder) {
        builder.newline().append("<path")
        for (i in attributes) {
            builder.append(" ${i.first}=\"").append(i.second).append("\"")
        }
        builder.append(" d=\"").indent()
        for (child in children) {
            builder.newline().append(child.name ?: "")
            for (i in child.attributes) {
                builder.append(" ${i.second}")
            }
        }
        builder.unindent().newline().append("\" />")
    }
}