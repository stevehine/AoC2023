package common

class Vec2(val x: Int, val y: Int) {
    override fun equals(other: Any?): Boolean = (other is Vec2) && other.x == x && other.y == y

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "$x,$y"
    }
}