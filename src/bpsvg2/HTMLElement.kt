package bpsvg2

import bpsvg2.eat.OutputBuilder
import bpsvg2.eat.OutputMode

class HTMLElement(tag: String? = null, root: Boolean = false) : Element(OutputMode.HTML, tag, root) {

    val a: HTMLElement get() = makeChild(this, "a")
    val abbr: HTMLElement get() = makeChild(this, "abbr")
    val address: HTMLElement get() = makeChild(this, "address")
    val area: HTMLElement get() = makeChild(this, "area")
    val article: HTMLElement get() = makeChild(this, "article")
    val aside: HTMLElement get() = makeChild(this, "aside")
    val audio: HTMLElement get() = makeChild(this, "audio")
    val b: HTMLElement get() = makeChild(this, "b")
    val base: HTMLElement get() = makeChild(this, "base")
    val bdi: HTMLElement get() = makeChild(this, "bdi")
    val bdo: HTMLElement get() = makeChild(this, "bdo")
    val blockquote: HTMLElement get() = makeChild(this, "blockquote")
    val body: HTMLElement get() = makeChild(this, "body")
    val br: HTMLElement get() = makeChild(this, "br")
    val button: HTMLElement get() = makeChild(this, "button")
    val canvas: HTMLElement get() = makeChild(this, "canvas")
    val caption: HTMLElement get() = makeChild(this, "caption")
    val cite: HTMLElement get() = makeChild(this, "cite")
    val code: HTMLElement get() = makeChild(this, "code")
    val col: HTMLElement get() = makeChild(this, "col")
    val colgroup: HTMLElement get() = makeChild(this, "colgroup")
    val data: HTMLElement get() = makeChild(this, "data")
    val datalist: HTMLElement get() = makeChild(this, "datalist")
    val dd: HTMLElement get() = makeChild(this, "dd")
    val del: HTMLElement get() = makeChild(this, "del")
    val details: HTMLElement get() = makeChild(this, "details")
    val defn: HTMLElement get() = makeChild(this, "defn")
    val dialog: HTMLElement get() = makeChild(this, "dialog")
    val div: HTMLElement get() = makeChild(this, "div")
    val dl: HTMLElement get() = makeChild(this, "dl")
    val dt: HTMLElement get() = makeChild(this, "dt")
    val em: HTMLElement get() = makeChild(this, "em")
    val embed: HTMLElement get() = makeChild(this, "embed")
    val fencedframe: HTMLElement get() = makeChild(this, "fencedframe")
    val fieldset: HTMLElement get() = makeChild(this, "fieldset")
    val figcaption: HTMLElement get() = makeChild(this, "figcaption")
    val figure: HTMLElement get() = makeChild(this, "figure")
    val footer: HTMLElement get() = makeChild(this, "footer")
    val form: HTMLElement get() = makeChild(this, "form")
    val h1: HTMLElement get() = makeChild(this, "h1")
    val h2: HTMLElement get() = makeChild(this, "h2")
    val h3: HTMLElement get() = makeChild(this, "h3")
    val h4: HTMLElement get() = makeChild(this, "h4")
    val h5: HTMLElement get() = makeChild(this, "h5")
    val h6: HTMLElement get() = makeChild(this, "h6")
    val head: HTMLElement get() = makeHead()
    val header: HTMLElement get() = makeChild(this, "header")
    val hgroup: HTMLElement get() = makeChild(this, "hgroup")
    val hr: HTMLElement get() = makeChild(this, "hr")
    val html: HTMLElement get() = makeChild(this, "html")
    val i: HTMLElement get() = makeChild(this, "i")
    val iframe: HTMLElement get() = makeChild(this, "iframe")
    val img: HTMLElement get() = makeChild(this, "img")
    val input: HTMLElement get() = makeChild(this, "input")
    val ins: HTMLElement get() = makeChild(this, "ins")
    val kbd: HTMLElement get() = makeChild(this, "kbd")
    val label: HTMLElement get() = makeChild(this, "label")
    val legend: HTMLElement get() = makeChild(this, "legend")
    val li: HTMLElement get() = makeChild(this, "li")
    val link: HTMLElement get() = makeChild(this, "link")
    val main: HTMLElement get() = makeChild(this, "main")
    val map: HTMLElement get() = makeChild(this, "map")
    val mark: HTMLElement get() = makeChild(this, "mark")
    val menu: HTMLElement get() = makeChild(this, "menu")
    val meta: HTMLElement get() = makeChild(this, "meta")
    val meter: HTMLElement get() = makeChild(this, "meter")
    val nav: HTMLElement get() = makeChild(this, "nav")
    val noscript: HTMLElement get() = makeChild(this, "noscript")
    val obj: HTMLElement get() = makeChild(this, "object")
    val ol: HTMLElement get() = makeChild(this, "ol")
    val optgroup: HTMLElement get() = makeChild(this, "optgroup")
    val option: HTMLElement get() = makeChild(this, "option")
    val output: HTMLElement get() = makeChild(this, "output")
    val p: HTMLElement get() = makeChild(this, "p")
    val picture: HTMLElement get() = makeChild(this, "picture")
    val portal: HTMLElement get() = makeChild(this, "portal")
    val pre: HTMLElement get() = makeChild(this, "pre")
    val progress: HTMLElement get() = makeChild(this, "progress")
    val q: HTMLElement get() = makeChild(this, "q")
    val rp: HTMLElement get() = makeChild(this, "rp")
    val rt: HTMLElement get() = makeChild(this, "rt")
    val ruby: HTMLElement get() = makeChild(this, "ruby")
    val s: HTMLElement get() = makeChild(this, "s")
    val samp: HTMLElement get() = makeChild(this, "samp")
    val script: HTMLElement get() = makeChild(this, "script")
    val search: HTMLElement get() = makeChild(this, "search")
    val section: HTMLElement get() = makeChild(this, "section")
    val select: HTMLElement get() = makeChild(this, "select")
    val slot: HTMLElement get() = makeChild(this, "slot")
    val small: HTMLElement get() = makeChild(this, "small")
    val source: HTMLElement get() = makeChild(this, "source")
    val span: HTMLElement get() = makeChild(this, "span")
    val strong: HTMLElement get() = makeChild(this, "strong")
    val style: CSSElement get() = makeStyle()
    val sub: HTMLElement get() = makeChild(this, "sub")
    val summary: HTMLElement get() = makeChild(this, "summary")
    val sup: HTMLElement get() = makeChild(this, "sup")
    val svg: SVGElement get() = SVGElement.makeChild(this, "svg")
    val table: HTMLElement get() = makeChild(this, "table")
    val tbody: HTMLElement get() = makeChild(this, "tbody")
    val td: HTMLElement get() = makeChild(this, "td")
    val template: HTMLElement get() = makeChild(this, "template")
    val textarea: HTMLElement get() = makeChild(this, "textarea")
    val tfoot: HTMLElement get() = makeChild(this, "tfoot")
    val th: HTMLElement get() = makeChild(this, "th")
    val thead: HTMLElement get() = makeChild(this, "thead")
    val time: HTMLElement get() = makeChild(this, "time")
    val title: HTMLElement get() = makeChild(this, "title")
    val tr: HTMLElement get() = makeChild(this, "tr")
    val track: HTMLElement get() = makeChild(this, "track")
    val u: HTMLElement get() = makeChild(this, "u")
    val ul: HTMLElement get() = makeChild(this, "ul")
    val varia: HTMLElement get() = makeChild(this, "var")
    val video: HTMLElement get() = makeChild(this, "video")
    val xmp: HTMLElement get() = makeChild(this, "xmp")

    companion object {

        fun makeChild(parent: Element, tag: String? = null): HTMLElement {
            val child = HTMLElement(tag)
            parent.addChild(child)
            return child
        }

        fun root(vararg attributes: Attribute, operation: HTMLOperation? = null): HTMLElement {
            val root = HTMLElement()
            val head = SVGElement("!DOCTYPE html")
            root.addChild(head)
            val body = HTMLElement("html")
            body.addAttributes(*attributes)
            if (operation != null) body.operation()
            root.addChild(body)
            return root
        }
    }

    fun makeStyle(): CSSElement {
        return CSSElement.makeChild(makeChild(this, "style"))
    }

    fun makeHead(): HTMLElement {
        return makeChild(this, "head")() {
            meta("charset" to "UTF-8")
        }
    }

    operator fun invoke(vararg attributes: Attribute, operation: HTMLOperation? = null): HTMLElement {
        for (i in attributes) {
            addAttribute(i)
        }
        if (operation != null) this.operation()
        return this
    }
}