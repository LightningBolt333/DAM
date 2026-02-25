package dam.exer_3

fun main(args: Array<String>) {
    val sequence = generateSequence(100.0){
        height -> height * 0.6
    }
        //generateSequence aplica sequencialmente o lambda a partir da seed
        .takeWhile { it >= 1 } //seleciona da sequencia todos os maiores que 1

    println(sequence.toList())



















}
