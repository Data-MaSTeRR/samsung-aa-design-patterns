package src.decorator;

/**
 * [Decorator - ConcreteDecorator]
 * 법적 증거 보존을 위한 위변조 방지 SHA-256 무결성 해시 생성 전처리.
 */
public class Hash extends UploadDecorator {
    public Hash(Uploader inner) {
        super(inner);
    }

    @Override
    protected String step() {
        return "위변조방지 해시";
    }
}
