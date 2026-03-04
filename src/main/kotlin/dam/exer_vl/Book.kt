package dam.exer_vl

abstract class Book(val title: String,
    val author: String,
    val publicationYear: Int,
    initialCopies: Int){

    // getter da era baseada no ano
    val era: String get() = when{
        publicationYear < 1980 -> "Classic"
        publicationYear in 1980..2010 -> "Modern"
        else -> "Contemporary"
    }

    // setter de cópias disponiveis
    var availableCopies: Int = initialCopies
        set(value){
            if(value < 0){
                field = 0 // field guarda o valor em memória
            } else {
                field = value
                if(field==0){
                   println("Warning: Book is now out of stock!")
                }
            }
        }
    init {
        println("Book '$title' by $author has been added to the library.")
    }


}