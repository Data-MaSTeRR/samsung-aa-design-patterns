package src;

import src.bridge.ColdStorage;
import src.bridge.HotStorage;
import src.bridge.Photo;
import src.bridge.Video;
import src.composite.EvidenceFolder;
import src.composite.MediaFile;
import src.decorator.*;
import src.proxy.VideoProxy;
import src.proxy.Viewable;

/** 3조 발표 데모: 손해사정 사진·영상 처리·저장 (패턴 4개를 문제 순서대로 보여 준다) */
public class Main {
    public static void main(String[] args) {
        // 문제 1. 폴더 속 폴더 → Composite
        System.out.println("■ 1. Composite - 어느 단계에서든 같은 질문");
        EvidenceFolder round1 = new EvidenceFolder("1회차")
                .add(new MediaFile("photo-01", 20))
                .add(new MediaFile("video-07", 1200));
        EvidenceFolder round2 = new EvidenceFolder("2회차")
                .add(new MediaFile("video-08", 600));
        EvidenceFolder accident = new EvidenceFolder("AC-2081").add(round1).add(round2);
        System.out.println("   1회차     : " + round1.count() + "건, " + round1.size() + "MB");
        System.out.println("   사고 전체 : " + accident.count() + "건, " + accident.size() + "MB");

        // 문제 2. 출처마다 다른 처리 → Decorator
        System.out.println("■ 2. Decorator - 출처마다 처리 단계 조합이 다르다");
        Uploader customerApp = new Mask(new Compress(new BasicUploader()));
        Uploader fieldSurvey = new Hash(new Compress(new BasicUploader()));
        System.out.println("   고객 앱  : " + customerApp.upload());
        System.out.println("   현장조사 : " + fieldSurvey.upload());

        // 문제 3. 종류 × 저장소 → Bridge
        System.out.println("■ 3. Bridge - 종류와 저장소를 자유롭게 조합");
        System.out.println("   " + new Video("video-07", new HotStorage()).upload());
        System.out.println("   " + new Photo("photo-01", new ColdStorage()).upload());

        // 문제 4. 무거운 영상, 민감한 의료 자료 → Proxy
        System.out.println("■ 4. Proxy - 필요할 때만 로드, 권한 확인");
        Viewable video = new VideoProxy(false);
        System.out.println("   (목록 조회: 아직 로드 안 함)");
        System.out.println("   재생 요청 → " + video.open("손해사정사"));
        Viewable medical = new VideoProxy(true);
        System.out.println("   의료 자료, 콜센터 상담원 → " + medical.open("콜센터 상담원"));
        System.out.println("   의료 자료, 손해사정사   → " + medical.open("손해사정사"));
    }
}
