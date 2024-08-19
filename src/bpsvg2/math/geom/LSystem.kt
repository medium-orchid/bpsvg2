package bpsvg2.math.geom

import bpsvg2.*
import bpsvg2.math.d2.Mat2D

class LSystem {

    companion object {
        private val identifier = Regex("[A-Za-z]\\d*")
        private val tokens = Regex("([A-Za-z]\\d*)|[()]")
    }

    class State {
        var orientation = Mat2D.id
            set(value) {
                partialPosition = value.inverse() * field * partialPosition
                field = value
            }

        private var partialPosition = Mat2D.id

        val position get() = orientation * partialPosition

        fun move(m: Mat2D) {
            partialPosition = m * partialPosition
        }

        var iteration = 0

        fun clone(): State {
            val new = State()
            new.orientation = orientation
            new.partialPosition = partialPosition
            new.iteration = iteration
            return new
        }

        fun copy(other: State) {
            orientation = other.orientation
            partialPosition = other.partialPosition
            iteration = other.iteration
        }

        fun next(): State {
            val n = clone()
            n.iteration += 1
            return n
        }
    }

    private val variables = mutableMapOf<String, String>()
    private val constants = mutableMapOf<String, SVGStateOperation>()
    private val aliases = mutableMapOf<String, String>()

    private fun verifyNew(name: String) {
        if (!identifier.matches(name)) throw IllegalArgumentException("$name is not a valid identifier")
        if (variables.containsKey(name) || constants.containsKey(name) || aliases.containsKey(name)) {
            throw IllegalArgumentException("$name is already defined")
        }
    }

    private fun alias(str: String): String {
        var out = str
        for ((k, v) in aliases) {
            out = out.replace(k, v)
        }
        return out
    }

    private fun perform(element: SVGElement, state: State, prev: Map<String, State>, name: String) {
        val v = variables[name]
        if (v != null) {
            element.use(href("$name${state.iteration - 1}"), "transform" to state.position)
            return
        }
        val c = constants[name]
        if (c != null) {
            element.c(state)
            return
        }
        throw IllegalArgumentException("$name is not defined")
    }

    fun perform(element: SVGElement, iterations: Int) {
        if (iterations <= 0) throw IllegalArgumentException("iterations must be positive")
        val aliased = mutableMapOf<String, Iterable<String>>()
        for ((k, v) in variables) {
            aliased[k] = tokens.findAll(v).map { x -> x.value }.toList()
        }
        var previousMap = variables.keys.associateWith { _ -> State() }
        for (i in 0..<iterations) {
            val stack = ArrayDeque<State>()
            val nextMap = previousMap.mapValues { (_, v) -> v.next() }
            for ((k, v) in aliased) {
                val n = nextMap[k]!!
                element.g(id("$k${n.iteration}")) {
                    for (j in v) {
                        when (j) {
                            "(" -> stack.addLast(n.clone())
                            ")" -> n.copy(stack.removeLast())
                            else -> perform(this, n, previousMap, j)
                        }
                    }
                }
            }
            previousMap = nextMap
        }
    }

    fun addVariable(name: String, value: String): LSystem {
        verifyNew(name)
        variables[name] = value
        return this
    }

    fun addOrientationConstant(name: String, operation: Mat2D): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            state.orientation = operation * state.orientation
        }
        return this
    }

    fun addMovementConstant(name: String, operation: Mat2D): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            state.move(operation)
        }
        return this
    }

    fun addUseConstant(name: String, use: String): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            use(href(use), "transform" to state.position)
        }
        return this
    }

    fun addConstant(name: String, operation: SVGStateOperation): LSystem {
        verifyNew(name)
        constants[name] = operation
        return this
    }

    fun addAlias(name: String, value: String): LSystem {
        verifyNew(name)
        aliases[name] = value
        return this
    }
}