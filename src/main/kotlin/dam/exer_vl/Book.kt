package dam.exer_vl

class Book(val title: String,
    val author: String,
    val publicationYear: Int,
    initialCopies: Int){

    // getter da era baseada no ano
    val era: String get() = when{
        publicationYear < 1980 -> "Classic"
        publicationYear in 1980..2010 -> "Modern"
        else -> "Contemporary"
    }





}