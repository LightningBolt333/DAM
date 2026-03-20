package dam

sealed class Event {
    //Classes sealed são usadas para representar uma hierarquia de classes restrita. Netse caso é ideal porque um evento só pode ser destes 3 tipos
    //é como se fosse um enum, mas cada constante pode ser uma classe que tem a sua própria informação
    data class Login(val username: String, val timestamp: Long)
    data class Purchase(val username: String, val amount: Double, val timestamp: Long)
    data class Logout(val username: String, val timestamp: Long)
}