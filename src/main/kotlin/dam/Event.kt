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
fun processEvent(events: List<Event>, handler: (Event) -> Unit) {
    events.forEach {handler(it)}
}