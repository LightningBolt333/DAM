package dam.ex1_2

class Cache<K, V> {
    //genéricos permitem criar classes que funcionam com qualquer tipo de data type
    private val storage = mutableMapOf<K, V>()
    //mutable maps guarda pares de objetos e suporta retornar o valor correspondente a cada key

    fun put(key: K, value: V) {
        storage[key] = value
    }

    fun get(key: K): V? = storage[key]

    fun evict(key: K){
        storage.remove(key)
    }

    fun size(): Int = storage.size

    fun getOrPut(key: K, default: () -> V): V{
        val current = get(key)
        return if (current != null) {
            current
        }else{
            val defaultValue = default()
            put(key, defaultValue)
            defaultValue
        }
    }

    //em kotlin podemos retornar o resultado de um if sem ter return statements
    fun transform(key: K, action: (V) -> V): Boolean {
        val currentV = get(key)
        return if(currentV != null){
            val newVal = action(currentV)
            put(key, newVal)
            true
        }else{
            false
        }
    }

    fun snapshot(): Map<K, V> {
       return storage.toMap()
    }

    fun filterValues(predicate: (V) -> Boolean): Map<K, V>{
        return storage.filterValues(predicate)
    }
}

fun main(){
    println("--- Word frequency cache ---")
    val wordCache = Cache<String, Int>()
    wordCache.put("kotlin", 1)
    wordCache.put("scala", 1)
    wordCache.put("haskell", 1)

    println("Size: ${wordCache.size()}")
    println("Frequency of \"kotlin\": ${wordCache.get("kotlin")}")

    println("getOrPut \"kotlin\": ${wordCache.getOrPut("kotlin") { 0 }}")
    println("getOrPut \"java\": ${wordCache.getOrPut("java") { 0 }}")
    println("Size after getOrPut: ${wordCache.size()}")

    val transformed = wordCache.transform("kotlin") { it + 1 }
    println("Transform \"kotlin\" (+1): $transformed")

    val missingTransformed = wordCache.transform("cobol") { it + 1 }
    println("Transform \"cobol\" (+1): $missingTransformed")

    println("Snapshot: ${wordCache.snapshot()}")

    println("\n--- Id registry cache ---")
    val idRegistry = Cache<Int, String>()
    idRegistry.put(1, "Alice")
    idRegistry.put(2, "Bob")

    println("Id 1 -> ${idRegistry.get(1)}")
    println("Id 2 -> ${idRegistry.get(2)}")

    idRegistry.evict(1)
    println("After evict id 1, size: ${idRegistry.size()}")
    println("Id 1 after evict -> ${idRegistry.get(1)}")
}

