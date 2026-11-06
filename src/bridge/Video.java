package src.bridge;

/**
 * [Bridge - RefinedAbstraction]
 * 대용량 동영상 미디어.
 */
public class Video extends Media {
    public Video(String name, int size, Storage storage) {
        super(name, size, storage);
    }

    @Override
    public String kind() {
        return "영상";
    }
}
