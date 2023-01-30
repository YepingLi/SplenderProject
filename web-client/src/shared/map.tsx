export class CustomMap<K, V> extends Map<K, V> {
    putIfAbsent(key: K, value: V) {
        if (!this.has(key)) {
            this.set(key, value);
        }
        return this;
    }
}