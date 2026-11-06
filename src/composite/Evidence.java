package src.composite;

/**
 * [Composite - Component]
 * 사고 폴더, 조사 회차 폴더, 개별 미디어 파일 모두가 공유하는 공통 인터페이스.
 */
public interface Evidence {
    String name();
    int size();              // 총 용량 (MB)
    int count();             // 총 파일 수
    String open(String role); // 열람/재생 요청
}
