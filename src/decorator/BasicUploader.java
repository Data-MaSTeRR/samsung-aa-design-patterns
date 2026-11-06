package src.decorator;

/**
 * [Decorator - ConcreteComponent]
 * 가장 안쪽의 기본 업로드 수신 처리.
 */
public class BasicUploader implements Uploader {
    @Override
    public String process(String fileInfo) {
        return fileInfo + " [수신]";
    }
}
