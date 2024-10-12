package bpsvg2.math.d3

import bpsvg2.math.d2.Vec2

data class Proj32(val x: Vec3, val y: Vec3, val v: Vec2 = Vec2.zero) {
    companion object {
        val dropZ = Proj32(Vec3.X, Vec3.Y)
    }

    operator fun times(other: Vec3): Vec2 {
        return Vec2(x.dot(other), y.dot(other)) + v
    }
}