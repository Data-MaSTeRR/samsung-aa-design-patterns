package src.decorator;

/** 안쪽 처리를 먼저 하고, 내 단계를 덧붙인다. */
public abstract class UploadDecorator implements Uploader {
    private final Uploader inner;

    protected UploadDecorator(Uploader inner) {
        this.inner = inner;
    }

    protected abstract String step();

    @Override
    public String upload() {
        return inner.upload() + " → " + step();
    }
}
