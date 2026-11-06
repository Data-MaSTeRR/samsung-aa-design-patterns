package src.bridge;

public class Photo extends Media {
    public Photo(String name, Storage storage) { super(name, storage); }

    @Override
    protected String kind() { return "사진"; }
}
