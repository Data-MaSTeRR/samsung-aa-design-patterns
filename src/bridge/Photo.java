package src.bridge;

/**
 * [Bridge - RefinedAbstraction]
 * 사진 미디어.
 */
public class Photo extends Media {
    public Photo(String name, int size, Storage storage) {
        super(name, size, storage);
    }

    @Override
    public String kind() {
        return "사진";
    }
}
