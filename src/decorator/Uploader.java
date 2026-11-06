package src.decorator;

/**
 * [Decorator - Component]
 * 업로드 전처리 파이프라인 인터페이스.
 */
public interface Uploader {
    String process(String fileInfo);
}
