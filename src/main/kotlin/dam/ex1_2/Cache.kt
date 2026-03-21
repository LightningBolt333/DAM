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
}