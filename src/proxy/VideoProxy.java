package src.proxy;

/** 진짜 영상 앞에 서는 대리인. ① 권한 확인 ② 처음 열 때만 로드 */
public class VideoProxy implements Viewable {
    private final boolean medical;   // 의료 관련 자료인가?
    private RealVideo real;          // 처음엔 비어 있다

    public VideoProxy(boolean medical) {
        this.medical = medical;
    }

    @Override
    public String open(String role) {
        if (medical && !role.equals("손해사정사")) {
            return "거절(권한 없음)";
        }
        if (real == null) {
            real = new RealVideo();  // 처음 열 때만 만든다
        }
        return real.open(role);
    }
}
