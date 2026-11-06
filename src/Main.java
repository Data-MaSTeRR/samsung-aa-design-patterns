package src;

import src.bridge.ColdStorage;
import src.bridge.HotStorage;
import src.bridge.Storage;
import src.composite.EvidenceFolder;
import src.decorator.BasicUploader;
import src.decorator.Compress;
import src.decorator.Hash;
import src.decorator.Mask;
import src.decorator.Uploader;
import src.proxy.MediaProxy;

/**
 * 3조 손해사정 미디어 처리·저장 통합 시스템
 * 
 * [4개 디자인 패턴이 톱니바퀴처럼 맞물리는 End-to-End 시나리오]
 * 1. Decorator : 출처별로 필요한 가공 단계(압축/마스킹/해시)를 조립하여 미디어 전처리
 * 2. Bridge    : 미디어 종류(사진/영상)와 물리적 저장소(고속/보관)를 직교 분리하여 저장
 * 3. Composite : 사고 → 회차 → 파일로 이어지는 계층 트리를 구성하여 일관된 크기/건수 집계
 * 4. Proxy     : Composite 트리의 Leaf로서 가벼운 메타데이터로 즉시 응답하고,
 *                open() 요청 시 권한 검사(Protection) 및 원본 지연 로드(Virtual) 수행
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("======================================================================");
        System.out.println(" 🚗 3조 손해사정 미디어 처리·저장 통합 시스템 (Direct Auto Insurance) ");
        System.out.println("======================================================================\n");

        // ---------------------------------------------------------------------
        // STEP 1. 인프라 준비: 저장소(Bridge) & 가공 파이프라인(Decorator)
        // ---------------------------------------------------------------------
        Storage hotStorage = new HotStorage();   // 고속 SSD 스토리지 (Bridge Implementor)
        Storage coldStorage = new ColdStorage(); // 장기 보관용 클라우드 (Bridge Implementor)

        // Decorator: 고객 앱 파이프라인 (기본수신 → 압축 → 개인정보 마스킹)
        Uploader customerPipeline = new Mask(new Compress(new BasicUploader()));

        // Decorator: 현장 직원 파이프라인 (기본수신 → 압축 → 위변조방지 해시)
        Uploader fieldPipeline = new Hash(new Compress(new BasicUploader()));

        // ---------------------------------------------------------------------
        // STEP 2. 증빙 계층 구조 생성 (Composite)
        // ---------------------------------------------------------------------
        EvidenceFolder accident = new EvidenceFolder("사고(AC-2081)");
        EvidenceFolder round1 = new EvidenceFolder("1차 현장조사");
        EvidenceFolder round2 = new EvidenceFolder("2차 정밀조사");
        accident.add(round1).add(round2);

        // ---------------------------------------------------------------------
        // STEP 3. 미디어 인입 및 증빙 트리 등록 (Decorator ➔ Bridge ➔ Proxy in Composite)
        // ---------------------------------------------------------------------
        System.out.println("【STEP 1】 미디어 수집 & 전처리 (Decorator ➔ Bridge 저장)");

        // 1) 고객 앱: 접촉 부위 사진 등록 (고속저장소)
        String photoLog = customerPipeline.process("photo-01.jpg (20MB)");
        System.out.println("  • 고객 앱 인입  : " + photoLog);
        round1.add(new MediaProxy("photo-01.jpg", 20, "사진", false, hotStorage));

        // 2) 현장 직원: 1.2GB 블랙박스/현장 영상 등록 (고속저장소)
        String videoLog = fieldPipeline.process("video-07.mp4 (1200MB)");
        System.out.println("  • 현장 출동 인입: " + videoLog);
        round1.add(new MediaProxy("video-07.mp4", 1200, "영상", false, hotStorage));

        // 3) 현장 직원: 병원 진단서/의료 사진 등록 (민감 의료정보=true, 장기보관소 저장)
        String medicalLog = fieldPipeline.process("medical-01.jpg (30MB)");
        System.out.println("  • 병원 진단 인입: " + medicalLog);
        round2.add(new MediaProxy("medical-01.jpg", 30, "사진", true, coldStorage));

        // ---------------------------------------------------------------------
        // STEP 4. 일관된 계층별 증빙 조회 (Composite + Virtual Proxy 메타데이터 응답)
        // ---------------------------------------------------------------------
        System.out.println("\n【STEP 2】 증빙 계층별 용량/건수 조회 (Composite - 일관된 .size(), .count())");
        System.out.println("  ※ Virtual Proxy 덕분에 1.2GB 원본을 전혀 로드하지 않고 0.001초 만에 응답!");
        System.out.println("  • " + round1.name() + "     ➔ 총 " + round1.count() + "건, " + round1.size() + "MB");
        System.out.println("  • " + round2.name() + "     ➔ 총 " + round2.count() + "건, " + round2.size() + "MB");
        System.out.println("  • " + accident.name() + "   ➔ 총 " + accident.count() + "건, " + accident.size() + "MB");

        // ---------------------------------------------------------------------
        // STEP 5. 미디어 열람 및 지연 로딩 / 보안 접근 통제 (Proxy)
        // ---------------------------------------------------------------------
        System.out.println("\n【STEP 3】 미디어 열람 & 접근 제어 (Proxy - Protection & Virtual)");

        // [시나리오 A] 대용량 영상 재생 요청 (손해사정사) ➔ Virtual Proxy: 이때 비로소 1.2GB 원본 로드
        System.out.println("  (1) 손해사정사가 대용량 현장 영상 재생 요청:");
        System.out.println("      " + accident.openMedia("video-07.mp4", "손해사정사"));

        // [시나리오 B] 민감 의료 사진 열람 요청 (콜센터 상담원) ➔ Protection Proxy: 원천 차단
        System.out.println("\n  (2) 콜센터 상담원이 피해자 상해 의료사진 열람 요청:");
        System.out.println("      " + accident.openMedia("medical-01.jpg", "콜센터 상담원"));

        // [시나리오 C] 민감 의료 사진 열람 요청 (손해사정사) ➔ Protection Proxy 통과 후 원본 로드
        System.out.println("\n  (3) 정당한 권한을 가진 손해사정사가 의료사진 열람 요청:");
        System.out.println("      " + accident.openMedia("medical-01.jpg", "손해사정사"));

        // ---------------------------------------------------------------------
        // STEP 6. 변경 시나리오: 다중 추돌 '사고군(Cluster)' 상위 묶음 확장 (Composite 무수정 확장)
        // ---------------------------------------------------------------------
        System.out.println("\n【STEP 4】 변경 시나리오: 다중 추돌 '사고군(CL-104)' 상위 계층 추가");
        EvidenceFolder cluster = new EvidenceFolder("연쇄사고군(CL-104)");
        EvidenceFolder accident2 = new EvidenceFolder("사고(AC-2082)");
        accident2.add(new MediaProxy("photo-99.jpg", 15, "사진", false, hotStorage));

        cluster.add(accident).add(accident2);
        System.out.println("  • " + cluster.name() + " 전체 집계 ➔ 총 " + cluster.count() + "건, " + cluster.size() + "MB");
        System.out.println("  ➔ 기존 핵심 코드 수정 없이 Composite 트리를 한 단계 더 감싸는 것만으로 무한 확장!");

        System.out.println("\n======================================================================");
        System.out.println("  모든 통합 시나리오 테스트가 정상적으로 완료되었습니다. ");
        System.out.println("======================================================================");
    }
}
