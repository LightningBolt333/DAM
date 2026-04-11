package dam.ex1_4
import kotlin.math.sqrt

data class Vec2(val x: Double, val y: Double) {
    operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
    operator fun minus(other: Vec2) = Vec2(x - other.x, y - other.y)
    operator fun times(scalar: Double) = Vec2(x * scalar, y * scalar)
    operator fun unaryMinus() = Vec2(-x, -y)

    fun magnitude(): Double = sqrt(x * x + y * y)

    operator fun compareTo(other: Vec2): Int {
        return this.magnitude().compareTo(other.magnitude())
    }

    fun dot(other: Vec2): Double = x * other.x + y * other.y

    fun normalized(): Vec2 {
        val magnitude = magnitude()
        if (magnitude == 0.0) throw IllegalStateException("Cannot normalize zero vector")
        return Vec2(x / magnitude, y / magnitude)
    }

    operator fun get(index: Int): Double = when (index) {
        0 -> x
        1 -> y
        else -> throw IndexOutOfBoundsException("Invalid vector index: $index")
    }

    operator fun Double.times(vec: Vec2): Vec2 = vec * this

    operator fun Vec2.component1() = x
    operator fun Vec2.component2() = y
}