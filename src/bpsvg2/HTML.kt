package bpsvg2

import bpsvg2.eat.OutputMode

class HTML(tag: String? = null, root: Boolean = false) : Element(OutputMode.XML, tag, root) {

    val a: HTML get() = makeChild(this, "a")
    val abbr: HTML get() = makeChild(this, "abbr")
    val address: HTML get() = makeChild(this, "address")
    val area: HTML get() = makeChild(this, "area")
    val article: HTML get() = makeChild(this, "article")
    val aside: HTML get() = makeChild(this, "aside")
    val audio: HTML get() = makeChild(this, "audio")
    val b: HTML get() = makeChild(this, "b")
    val base: HTML get() = makeChild(this, "base")
    val bdi: HTML get() = makeChild(this, "bdi")
    val bdo: HTML get() = makeChild(this, "bdo")
    val blockquote: HTML get() = makeChild(this, "blockquote")
    val body: HTML get() = makeChild(this, "body")
    val br: HTML get() = makeChild(this, "br")
    val button: HTML get() = makeChild(this, "button")
    val canvas: HTML get() = makeChild(this, "canvas")
    val caption: HTML get() = makeChild(this, "caption")
    val cite: HTML get() = makeChild(this, "cite")
    val code: HTML get() = makeChild(this, "code")
    val col: HTML get() = makeChild(this, "col")
    val colgroup: HTML get() = makeChild(this, "colgroup")
    val data: HTML get() = makeChild(this, "data")
    val datalist: HTML get() = makeChild(this, "datalist")
    val dd: HTML get() = makeChild(this, "dd")
    val del: HTML get() = makeChild(this, "del")
    val details: HTML get() = makeChild(this, "details")
    val defn: HTML get() = makeChild(this, "defn")
    val dialog: HTML get() = makeChild(this, "dialog")
    val div: HTML get() = makeChild(this, "div")
    val dl: HTML get() = makeChild(this, "dl")
    val dt: HTML get() = makeChild(this, "dt")
    val em: HTML get() = makeChild(this, "em")
    val embed: HTML get() = makeChild(this, "embed")
    val fencedframe: HTML get() = makeChild(this, "fencedframe")
    val fieldset: HTML get() = makeChild(this, "fieldset")
    val figcaption: HTML get() = makeChild(this, "figcaption")
    val figure: HTML get() = makeChild(this, "figure")
    val footer: HTML get() = makeChild(this, "footer")
    val form: HTML get() = makeChild(this, "form")
    val h1: HTML get() = makeChild(this, "h1")
    val head: HTML get() = makeHead()
    val header: HTML get() = makeChild(this, "header")
    val hgroup: HTML get() = makeChild(this, "hgroup")
    val hr: HTML get() = makeChild(this, "hr")
    val html: HTML get() = makeChild(this, "html")
    val i: HTML get() = makeChild(this, "i")
    val iframe: HTML get() = makeChild(this, "iframe")
    val img: HTML get() = makeChild(this, "img")
    val input: HTML get() = makeChild(this, "input")
    val ins: HTML get() = makeChild(this, "ins")
    val kbd: HTML get() = makeChild(this, "kbd")
    val label: HTML get() = makeChild(this, "label")
    val legend: HTML get() = makeChild(this, "legend")
    val li: HTML get() = makeChild(this, "li")
    val link: HTML get() = makeChild(this, "link")
    val main: HTML get() = makeChild(this, "main")
    val map: HTML get() = makeChild(this, "map")
    val mark: HTML get() = makeChild(this, "mark")
    val menu: HTML get() = makeChild(this, "menu")
    val meta: HTML get() = makeChild(this, "meta")
    val meter: HTML get() = makeChild(this, "meter")
    val nav: HTML get() = makeChild(this, "nav")
    val noscript: HTML get() = makeChild(this, "noscript")
    val obj: HTML get() = makeChild(this, "object")
    val ol: HTML get() = makeChild(this, "ol")
    val optgroup: HTML get() = makeChild(this, "optgroup")
    val option: HTML get() = makeChild(this, "option")
    val output: HTML get() = makeChild(this, "output")
    val p: HTML get() = makeChild(this, "p")
    val picture: HTML get() = makeChild(this, "picture")
    val portal: HTML get() = makeChild(this, "portal")
    val pre: HTML get() = makeChild(this, "pre")
    val progress: HTML get() = makeChild(this, "progress")
    val q: HTML get() = makeChild(this, "q")
    val rp: HTML get() = makeChild(this, "rp")
    val rt: HTML get() = makeChild(this, "rt")
    val ruby: HTML get() = makeChild(this, "ruby")
    val s: HTML get() = makeChild(this, "s")
    val samp: HTML get() = makeChild(this, "samp")
    val script: HTML get() = makeChild(this, "script")
    val search: HTML get() = makeChild(this, "search")
    val section: HTML get() = makeChild(this, "section")
    val select: HTML get() = makeChild(this, "select")
    val slot: HTML get() = makeChild(this, "slot")
    val small: HTML get() = makeChild(this, "small")
    val source: HTML get() = makeChild(this, "source")
    val span: HTML get() = makeChild(this, "span")
    val strong: HTML get() = makeChild(this, "strong")
    val style: CSS get() = makeStyle()
    val sub: HTML get() = makeChild(this, "sub")
    val summary: HTML get() = makeChild(this, "summary")
    val sup: HTML get() = makeChild(this, "sup")
    val svg: SVG get() = SVG.makeChild(this, "svg")
    val table: HTML get() = makeChild(this, "table")
    val tbody: HTML get() = makeChild(this, "tbody")
    val td: HTML get() = makeChild(this, "td")
    val template: HTML get() = makeChild(this, "template")
    val textarea: HTML get() = makeChild(this, "textarea")
    val tfoot: HTML get() = makeChild(this, "tfoot")
    val th: HTML get() = makeChild(this, "th")
    val thead: HTML get() = makeChild(this, "thead")
    val time: HTML get() = makeChild(this, "time")
    val title: HTML get() = makeChild(this, "title")
    val tr: HTML get() = makeChild(this, "tr")
    val track: HTML get() = makeChild(this, "track")
    val u: HTML get() = makeChild(this, "u")
    val ul: HTML get() = makeChild(this, "ul")
    val varia: HTML get() = makeChild(this, "var")
    val video: HTML get() = makeChild(this, "video")
    val xmp: HTML get() = makeChild(this, "xmp")

    companion object {
        fun makeChild(parent: Element, tag: String? = null): HTML {
            val child = HTML(tag)
            parent.addChild(child)
            return child
        }

        fun root(vararg attributes: Attribute, operation: HTMLOperation? = null): HTML {
            val root = HTML()
            val head = SVG("!doctype html")
            root.addChild(head)
            val body = HTML("html")
            body.addAttributes(*attributes)
            if (operation != null) body.operation()
            root.addChild(body)
            return root
        }
    }

    fun makeStyle(): CSS {
        return CSS.makeChild(makeChild(this, "style"))
    }

    fun makeHead(): HTML {
        return makeChild(this, "head")() {
            meta("charset" to "UTF-8")
        }
    }

    operator fun invoke(vararg attributes: Attribute, operation: HTMLOperation? = null): HTML {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) this.operation()
        return this
    }
}