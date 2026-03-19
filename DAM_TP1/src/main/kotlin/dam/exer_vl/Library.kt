package dam.exer_vl

class Library(val libraryName: String) {
    private val books = mutableListOf<Book>()

    fun addBook(book: Book) {
        books.add(book)
    }

    fun borrowBook(title: String){
        val book = books.find{it.title.equals(title, ignoreCase = true)}
        if(book != null){
            if(book.availableCopies > 0){
              book.availableCopies--
              println("Successfully borrowed '$title'! Remaining copies: ${book.availableCopies}")
            } else {
                println("'$title' is currently out of stock!")
            }
        } else {
            println("Book '$title' not currently found in the library!")
        }
    }

    fun returnBook(title: String){
        val book = books.find{it.title.equals(title, ignoreCase = true)}
        if(book != null){
            book.availableCopies++
            println("Book '$title' returned to library. Available copies: ${book.availableCopies}")
        } else {
            println("Error: Book '$title' does not belong to this library!")
        }
    }

    fun showBooks(){
        println("\n===: Library Catalog :===")
        books.forEach {println(it)}
    }

    fun searchByAuthor(author: String){
        println("\nBooks written by $author:")
        val results = books.filter{it.author.equals(author, ignoreCase = true)}
        if(results.isEmpty()){
            println("No books by $author found!")
        } else {
            results.forEach{println("-> ${it.title} (${it.availableCopies} available)")}
        }
    }

    companion object{
        private var totalBooksCreated: Int = 0
        fun getTotalBooksCreated(): Int = totalBooksCreated
    }
}

data class LibraryMember(
    val name: String,
    val membershipId: String,
    val borrowedBooks: MutableList<String> = mutableListOf()
)