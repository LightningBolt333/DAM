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
}

