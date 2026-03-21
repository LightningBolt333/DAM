package dam.ex1_3

class Pipeline {
    private val stages = mutableListOf<Pair<String, (List<String>) -> List<String>>>()

    fun addStage(name: String, transform: (List<String>) -> List<String>) {
        stages.add(name to transform)
        //keyword "to" cria um tuplo de name e transform, ou seja o setep
    }

    fun execute(input: List<String>): List<String> {
        var currentResult = input

        for((_, transform) in stages){
            currentResult = transform(currentResult)
        }
        return currentResult
    }
}