package src.proxy;

/** 진짜 영상. 만드는 순간 원본을 불러오므로 무겁다. */
public class RealVideo implements Viewable {
    public RealVideo() {
        System.out.println("   (원본 영상 로드 - 무거운 작업)");
    }

    @Override
    public String open(String role) { return "재생 시작"; }
}
