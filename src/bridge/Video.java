package src.bridge;

public class Video extends Media {
    public Video(String name, Storage storage) { super(name, storage); }

    @Override
    protected String kind() { return "영상"; }
}
