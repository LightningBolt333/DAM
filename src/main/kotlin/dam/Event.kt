package dam

sealed class Event {
    //Classes sealed são usadas para representar uma hierarquia de classes restrita. Netse caso é ideal porque um evento só pode ser destes 3 tipos
    //é como se fosse um enum, mas cada constante pode ser uma classe que tem a sua própria informação
    data class Login(val username: String, val timestamp: Long) : Event()
    data class Purchase(val username: String, val amount: Double, val timestamp: Long) : Event()
    data class Logout(val username: String, val timestamp: Long) : Event()
}

//extension functions permitem adicionar funcionalidades e métodos novos a classes já existentes, como List, sem alterar o seu source code
fun List<Event>.filterByUser(username: String): List<Event> {
    //pega no username e retorna os eventos a si associados
    return this.filter {
        when (it) {
            is Event.Login -> it.username == username
            is Event.Purchase -> it.username == username
            is Event.Logout -> it.username == username
        }
    }
}

fun List<Event>.totalSpent(username: String): Double {
    //pega no username e retorna o gasto total desse user, filtrado de todas as compras existentes
    return this.filterIsInstance<Event.Purchase>().filter {it.username == username}.sumOf {it.amount}
}

//higher-order functions são funções que usam outras funções como parâmetros
//também podem retornar uma função como resultado
//neste caso usa umafunção que pega num objeto Event e retorna nada (unit no kotlin é equivalente a void no java, mas é um objeto em vez de ser nada)
fun processEvents(events: List<Event>, handler: (Event) -> Unit) {
    events.forEach {handler(it)}
}

fun main() {

    val events = listOf (
        Event.Login ("alice", 1000),
        Event.Purchase ("alice", 49.99, 1100),
        Event.Purchase ("bob", 19.99, 1200),
        Event.Login ("bob", 1050),
        Event.Purchase ("alice", 15.00, 1300),
        Event.Logout ("alice", 1400),
        Event.Logout ("bob", 1500)
    )

    processEvents(events) { event ->
        when (event) {
            is Event.Login -> println("[LOGIN] ${event.username} logged in at t=${event.timestamp}")
            is Event.Purchase -> println("[PURCHASE] ${event.username} spent $${event.amount} at t=${event.timestamp}")
            is Event.Logout -> println("[LOGOUT] ${event.username} logged out at t=${event.timestamp}")

        }
    }

    println("Total spent by Alice: $${events.totalSpent("alice")}")
    println("Total spent by Bob: $${events.totalSpent("bob")}")
    println("Events for Alice: ")
    events.filterByUser("alice").forEach {println(it)}

}