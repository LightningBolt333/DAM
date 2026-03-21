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
            //destructing underscore pede ao compiler para ignorar a variável que substitui, porque não vai ser usada
            currentResult = transform(currentResult)
        }
        return currentResult
    }

    fun describe(){
        println("Pipeline stages: ")
        stages.forEachIndexed {index, (name, _) -> println("${index + 1}.$name")}
    }
}