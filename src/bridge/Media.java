package src.bridge;

/** 미디어 쪽 (무엇을). 저장소는 직접 알지 않고 Storage 로만 연결된다. */
public abstract class Media {
    private final String name;
    private final Storage storage;   // ← 이 한 줄이 "다리(bridge)"

    protected Media(String name, Storage storage) {
        this.name = name;
        this.storage = storage;
    }

    protected abstract String kind();

    public String upload() {
        return storage.save(kind() + " " + name);
    }
}
