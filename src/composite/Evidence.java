package src.composite;

/** 사고·회차·파일 어느 단계든 똑같이 묻는다. */
public interface Evidence {
    int size();   // 총 용량(MB)
    int count();  // 총 파일 수
}
