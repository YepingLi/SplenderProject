class Optional<T> {
    private data: T;
    private constructor(data: T) {
        this.data = data;
    }

    static ofEmpty() {
        return new Optional<any>(null);
    }

    static of<T>(data: T) {
        return new Optional<T>(data);
    }

    static ofNullable<T>(data: T) {
        if (data == null) {
            return Optional.ofEmpty();
        }
        return new Optional<T>(data);
    }

    private hasValue() {
        return this.data !== null && this.data !== undefined
    }

    public ifPresent(applier: (data: T) => void) {
        if (!this.hasValue()) {
            return this;
        }
        applier(this.data);
        return this;
    }

    public orElseGet(supplier: () => T) {
        if (this.hasValue()) {
            return this.data
        }

        return supplier();
    }

    public get() {
        if (this.hasValue()) {
            throw new Error("No such element!");
        }
        return this.data
    }

    public map<U>(consumer: (data: T) => U): Optional<U>{
        if (!this.hasValue()) {
            return Optional.ofEmpty();
        }
        return Optional.of(consumer(this.data));
    }

    public orElse(newData: T) {
        if (this.hasValue()) return this.data;
        return newData; 
    }

}


export default Optional;