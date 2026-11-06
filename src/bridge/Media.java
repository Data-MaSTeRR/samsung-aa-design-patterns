package src.bridge;

/**
 * [Bridge - Abstraction]
 * 미디어 추상 클래스. 저장소(Storage) 구현체에 호출을 위임하는 '다리(Bridge)' 필드를 포함.
 */
public abstract class Media {
    protected final String name;
    protected final int size;
    protected final Storage storage; // Bridge: 저장소 인터페이스로 위임

    protected Media(String name, int size, Storage storage) {
        this.name = name;
        this.size = size;
        this.storage = storage;
    }

    public abstract String kind();

    public String persist() {
        return storage.save(kind() + " " + name, size);
    }

    public String read() {
        return storage.load(kind() + " " + name);
    }

    public String name() {
        return name;
    }

    public int size() {
        return size;
    }
}
