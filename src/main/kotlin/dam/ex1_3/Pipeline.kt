package dam.ex1_3

class Pipeline {
    //uma pipeline é um padrão de design em que uma sequencia de estágios de processamento estão conectados em série, o output de um é o input da próxima
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

fun buildPipeline(action: Pipeline.() -> Unit): Pipeline {
    //criamos uma instancia da classe Pipeline, aplicamos o lambda e retornamos o resultado
    //neste caso action acaba com a necessidade de chamar a ação que pretendemos fazer diretamente todas as vezes
    val pipeline = Pipeline()
    pipeline.action()
    return pipeline
}

fun main() {
    val logs = listOf(
        " INFO : server started ",
        " ERROR : disk full ",
        " DEBUG : checking config ",
        " ERROR : out of memory ",
        " INFO : request received ",
        " ERROR : connection timeout "
    )

    val logPipeline = buildPipeline {
        addStage("Trim") { list ->
            list.map { it.trim() }
        }
        addStage("Filter errors") { list ->
            list.filter { it.contains("ERROR") }
        }
        addStage("Uppercase") { list ->
            list.map { it.uppercase() }
        }
        addStage("Add index") { list ->
            list.mapIndexed { index, line -> "${index + 1}. $line" }
        }
    }

    logPipeline.describe()

    val result = logPipeline.execute(logs)
    println("\nResult:")
    result.forEach { println(it) }
}