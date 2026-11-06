package src.decorator;

/**
 * [Decorator - ConcreteDecorator]
 * 대용량 미디어 용량 절감을 위한 압축 전처리.
 */
public class Compress extends UploadDecorator {
    public Compress(Uploader inner) {
        super(inner);
    }

    @Override
    protected String step() {
        return "압축";
    }
}
