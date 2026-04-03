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
}