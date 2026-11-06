package src.decorator;

public class Compress extends UploadDecorator {
    public Compress(Uploader inner) { super(inner); }

    @Override
    protected String step() { return "압축"; }
}
