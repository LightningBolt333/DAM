package dam.exer_2

fun main(args: Array<String>) {
    //Create a console-based calculator that allows users to perform various operations. Your calculator should support the following:
    //1. Basic arithmetic operations: Addition, subtraction, multiplication, and division.
    //2. Boolean operators: AND (&&), OR (||), NOT (!).
    //3. Bitwise shift operators: Left shift (shl), right shift (shr).
    //4. Show the results in decimal, hexadecimal, and boolean.
    while (true){
        println("=====Calculadora Simples=====")
        println("Escolha uma operação das seguintes:")
        println("1 -> Adição (+)")
        println("2 -> Subtração (-)")
        println("3 -> Multiplicação (*)")
        println("4 -> Divisão (/)")
        println("5 -> AND booleano (&&)")
        println("6 -> OR booleano (||)")
        println("7 -> NOT booleano (!)")
        println("8 -> Left Shift (shl)")
        println("9 -> Right Shift (shr)")
        println("0 -> Exit")
        println("Dê Enter da sua escolha: ")

        val escolha = readlnOrNull()?.toIntOrNull()
        //readLine lê o input do utilizador
        //usei o toIntOrNull para evitar erros de input. Se o valor não for um Int, passa a Null
        //o "?" identifica a string como Nullable, ou seja, pode ser nula
        try{
            when(escolha) {
                // Switch no Kotlin é feito através do When
                // Aritmética
                1, 2, 3, 4 -> {
                    //Caso escolha seja 1, 2, 3 ou 4...
                    var num1: Float? = null
                    var num2: Float? = null
                    while (num1 == null){
                        print("Insira o primeiro número: ")
                        num1 = readln().toFloat()
                    }

                    while (num2 == null){
                        print("Insira o segundo número: ")
                        num2 = readln().toFloat()
                    }


                    val resultado = when (escolha) {
                        1 -> num1 + num2
                        2 -> num1 - num2
                        3 -> num1 * num2
                        4 -> {
                            if (num2.equals(0f)){
                                throw ArithmeticException("Divisão por 0")
                            }
                            num1 / num2
                        }
                        else -> 0
                    }
                    println("\nResultado: $resultado")
                }
                // AND / OR
                5, 6 -> {
                    var b1: Boolean? = null
                    var b2: Boolean? = null
                    while(b1 == null){
                        print("Insira o primeiro booleano (true/false): ")
                        b1 = readln().toBoolean()
                    }
                    while(b2 == null){
                        print("Insira o segundo boolean (true/false): ")
                        b2 = readln().toBoolean()
                    }

                    val resultado = when(escolha) {
                        5 -> b1 && b2
                        6 -> b1 || b2
                        else -> false
                    }

                    println("\nResultado: $resultado")
                }

                // NOT
                7 -> {
                    var b: Boolean? = null
                    while (b == null){
                        print("Insira um booleano (true/false): ")
                        b = readln().toBoolean()
                    }

                    val resultado = !b
                    println("\nResultado: $resultado")
                }

                // bit shifts
                8, 9 -> {
                    var num: Int? = null
                    var shift: Int? = null

                    while(num == null){
                        print("Insira um número: ")
                        num = readln().toInt()
                    }

                    while(shift == null){
                        print("Insira a quantidade de bits a serem deslocados: ")
                        shift = readln().toInt()
                    }

                    val resultado = when (escolha){
                        8 -> num shl shift
                        9 -> num shr shift
                        else -> 0
                    }

                    println("\nResultado: $resultado")

                }

            }
        }

        catch (e: ArithmeticException) {
            println("Error: ${e.message}")
        }
    }
}