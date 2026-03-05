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
}