package src.decorator;

/** 위변조 방지 해시 */
public class Hash extends UploadDecorator {
    public Hash(Uploader inner) { super(inner); }

    @Override
    protected String step() { return "해시"; }
}
