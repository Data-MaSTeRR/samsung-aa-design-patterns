package src.decorator;

/**
 * [Decorator - Decorator]
 * 부가 기능을 덧붙이기 위한 추상 데코레이터.
 */
public abstract class UploadDecorator implements Uploader {
    protected final Uploader inner;

    protected UploadDecorator(Uploader inner) {
        this.inner = inner;
    }

    protected abstract String step();

    @Override
    public String process(String fileInfo) {
        return inner.process(fileInfo) + " → " + step();
    }
}
