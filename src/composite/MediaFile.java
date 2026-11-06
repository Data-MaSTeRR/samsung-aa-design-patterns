package src.composite;

/** 파일 1건 (맨 끝 잎사귀). */
public class MediaFile implements Evidence {
    private final String name;
    private final int mb;

    public MediaFile(String name, int mb) {
        this.name = name;
        this.mb = mb;
    }

    public int size()  { return mb; }
    public int count() { return 1; }
}
