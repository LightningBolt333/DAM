package dam.exer_vl

abstract class Book(val title: String, val author: String, val publicationYear: Int, initialCopies: Int){

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

    abstract fun getStorageInfo(): String

    override fun toString(): String{
        return "Title: $title, Author: $author, Era: $era, Available: $availableCopies copies"
    }
}

class DigitalBook(title: String, author: String, publicationYear: Int, availableCopies: Int,
    val fileSize: Double, val format: String): Book(title, author, publicationYear, availableCopies){
    override fun getStorageInfo(): String {
        return "Stored digitally: $fileSize MB, Format: $format"
    }

    override fun toString(): String{
        return super.toString() + "\nStorage: ${getStorageInfo()}"
    }
    }

class PhysicalBook(title: String, author: String, publicationYear: Int, availableCopies: Int,
    val weight: Int, val hasHardcover: Boolean = true): Book(title, author, publicationYear, availableCopies){

    override fun getStorageInfo(): String {
        val coverType = if(hasHardcover) "Yes" else "No"
        return "Physical book: ${weight}g, Hardcover: $coverType"
    }

    override fun toString(): String {
        return super.toString() + "\nStorage: ${getStorageInfo()}"
    }
    }