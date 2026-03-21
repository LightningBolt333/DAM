package dam.ex1_3

class Pipeline {
    private val steps = mutableListOf<Pair<String, (List<String>) -> List<String>>>()

    fun addStage(name: String, transform: (List<String>) -> List<String>){
        steps.add(name to transform)
        //keyword "to" cria um tuplo de name e transform, ou seja o setep
    }
}