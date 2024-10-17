package bpsvg2.geom

import bpsvg2.*
import bpsvg2.math.d2.Mat2D
import bpsvg2.math.d2.Trans2D

class LSystem {

    companion object {
        private val identifier = Regex("[A-Za-z]\\d*")
        private val tokens = Regex("([A-Za-z]\\d*)|[()]")
    }

    class State(var iteration: Int = 0) {
        var orientation = Trans2D.id
            set(value) {
                partialPosition = value.inverse() * field * partialPosition
                field = value
            }

        private var partialPosition = Trans2D.id

        val position get() = orientation * partialPosition

        val fullPosition get() = position * orientation

        fun move(m: Trans2D) {
            partialPosition = m * partialPosition
        }

        fun clone(): State {
            val new = State()
            new.orientation = orientation
            new.partialPosition = partialPosition
            return new
        }

        fun copy(other: State) {
            orientation = other.orientation
            partialPosition = other.partialPosition
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

    private fun perform(element: SVGElement, state: State, name: String) {
        val v = variables[name]
        if (v != null) {
            element.use(href("$name${state.iteration - 1}"), "transform" to state.fullPosition)
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
            aliased[k] = tokens.findAll(alias(v)).map {
                x -> x.value
            }.toList()
        }
        for (i in 0..<iterations) {
            val stack = ArrayDeque<State>()
            val states = variables.keys.associateWith { _ -> State(i + 1) }
            for ((k, v) in aliased) {
                val n = states[k]!!
                element.g(id("$k${i + 1}")) {
                    for (j in v) {
                        when (j) {
                            "(" -> stack.addLast(n.clone())
                            ")" -> n.copy(stack.removeLast())
                            else -> perform(this, n, j)
                        }
                    }
                }
            }
        }
    }

    fun addVariable(name: String, value: String): LSystem {
        verifyNew(name)
        variables[name] = value
        return this
    }

    fun addOrientationConstant(name: String, operation: Trans2D): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            state.orientation = operation * state.orientation
        }
        return this
    }

    fun addMovementConstant(name: String, operation: Trans2D): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            state.move(operation)
        }
        return this
    }

    fun addUseConstant(name: String, use: String): LSystem {
        verifyNew(name)
        constants[name] = { state ->
            use(href(use), "transform" to state.fullPosition)
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