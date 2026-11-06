package src.decorator;

/** 번호판·얼굴 마스킹 */
public class Mask extends UploadDecorator {
    public Mask(Uploader inner) { super(inner); }

    @Override
    protected String step() { return "마스킹"; }
}
