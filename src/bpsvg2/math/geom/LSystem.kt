package bpsvg2.math.geom

import bpsvg2.*
import bpsvg2.math.d2.Mat2D

class LSystem {

    companion object {
        private val identifier = Regex("[A-Za-z][^A-Za-z]?\\d+")
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
    }

    private val variables = mutableMapOf<String, SVGStateOperation>()
    private val constants = mutableMapOf<String, StateOperation>()
    private val aliases = mutableMapOf<String, String>()

    private fun verifyNew(name: String) {
        if (!identifier.matches(name)) throw IllegalArgumentException("$name is not a valid identifier")
        if (variables.containsKey(name) || constants.containsKey(name) || aliases.containsKey(name)) {
            throw IllegalArgumentException("$name is already defined")
        }
    }

    private fun perform(svgElement: SVGElement, state: State, name: String) {
        val v = variables[name]
        if (v != null) {
            svgElement.v(state)
            return
        }
        val c = constants[name]
        if (c != null) {
            c(state)
            return
        }
        throw IllegalArgumentException("$name is not defined")
    }

    fun addVariable(name: String, operation: SVGStateOperation? = null): LSystem {
        verifyNew(name)
        if (operation == null) {
            variables[name] = { state ->
                if (state.iteration > 0) {
                    use(id("$name${state.iteration}"))
                }
            }
        } else {
            variables[name] = operation
        }
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

    fun addConstant(name: String, operation: StateOperation): LSystem {
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