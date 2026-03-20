package dam

sealed class Event {
    data class Login(val username: String, val timestamp: Long)
    data class Purchase(val username: String, val amount: Double, val timestamp: Long)
    data class Logout(val username: String, val timestamp: Long)
}