fun main(args: Array<String>) {
    // Create and initialize an integer array with the first 50 perfect squares (1^2 , 2^2 , 3^2 , ..., 50^2) dinamically, using the following:
    // a) Using IntArray constructor;
    val arrayA = IntArray(50) {(it + 1) * (it + 1)}
    // O parâmetro do IntArray representa o seu tamanho, neste caso de 0 a 49
    println("a)")
    for (num in arrayA) {
        println(num)
    }

    // b) Using a range and map();
    val arrayB = (1..50).map {it * it}
    // Em Kotlin fazemos ranges com o formato (a..b), para um range de "a" a "b"
    // O map aplica o lambda associado para calcular o quadrado de cada número dentro do range
    println("b)")
    for (num in arrayB) {
        println(num)
    }

    // c) Using Array with constructor;
    val arrayC = Array(50 {(it + 1) * (it + 1)})
    //O construtor Array é uma forma mais genérica de IntArray, e funciona de forma idêntica
    println("c)")
    for (num in arrayC) {
        println(num)
    }
}

