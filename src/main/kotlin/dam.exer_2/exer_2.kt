package dam.exer_2

fun main(args: Array<String>) {
    //Create a console-based calculator that allows users to perform various operations. Your calculator should support the following:
    //1. Basic arithmetic operations: Addition, subtraction, multiplication, and division.
    //2. Boolean operators: AND (&&), OR (||), NOT (!).
    //3. Bitwise shift operators: Left shift (shl), right shift (shr).
    //4. Show the results in decimal, hexadecimal, and boolean.

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

    val escolha = readLine()?.toIntOrNull()
    //readLine lê o input do utilizador
    //usei o toIntOrNull para evitar erros de input. Se o valor não for um Int, passa a Null
    //o "?" identifica a string como Nullable, ou seja, pode ser nula
    try{
        when(escolha) {
            // Switch no Kotlin é feito através do When
            // Aritmética
            1, 2, 3, 4 -> {
                //Caso escolha seja 1, 2, 3 ou 4...
                print("Insira o primeiro número: ")
                val num1 = readLine()!!.toInt()
                print("Insira o segundo número: ")
                val num2 = readLine()!!.toInt()

                val resultado = when (escolha) {
                    1 -> num1 + num2
                    2 -> num1 - num2
                    3 -> num1 * num2
                    4 -> {
                        if (num2 == 0){
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
                print("Insira o primeiro booleano (true/false): ")
                val b1 = readLine()!!.toBoolean()
                print("Insira o segundo boolean (true/false): ")
                val b2 = readLine()!!.toBoolean()

                val resultado = when(escolha) {
                    5 -> b1 && b2
                    6 -> b1 || b2
                    else -> false
                }

                println("\nResultado: $resultado")
            }

            // NOT
            7 -> {
                print("Insira um booleano (true/false): ")
                val b = readLine()!!.toBoolean()
                val resultado = !b
                println("\nResultado: $resultado")
            }

        }
    }


    catch (e: ArithmeticException) {
        println("Error: ${e.message}")
    }
}