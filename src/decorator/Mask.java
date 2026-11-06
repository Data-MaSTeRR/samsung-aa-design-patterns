package src.decorator;

/**
 * [Decorator - ConcreteDecorator]
 * 개인정보 보호를 위한 차량 번호판 및 보행자 얼굴 마스킹 전처리.
 */
public class Mask extends UploadDecorator {
    public Mask(Uploader inner) {
        super(inner);
    }

    @Override
    protected String step() {
        return "개인정보 마스킹";
    }
}
