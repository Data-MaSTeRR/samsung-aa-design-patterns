package src.proxy;

import src.bridge.Media;
import src.bridge.Photo;
import src.bridge.Storage;
import src.bridge.Video;
import src.composite.Evidence;

/**
 * [Proxy - Virtual Proxy & Protection Proxy / Composite - Leaf]
 * 1) Composite의 Leaf 노드로서 증빙 트리에 등록됨.
 * 2) Virtual Proxy: 목록/용량 조회 시에는 원본을 로드하지 않고, open() 시점에만 실제 미디어 로드.
 * 3) Protection Proxy: 민감한 의료정보인 경우 허용된 역할(손해사정사)만 접근 허가.
 */
public class MediaProxy implements Evidence {
    private final String name;
    private final int size;
    private final String kind;       // "사진" 또는 "영상"
    private final boolean medical;   // 의료정보 여부
    private final Storage storage;   // Bridge 저장소
    private Media realMedia;         // 지연 생성되는 실제 미디어 (처음엔 null)

    public MediaProxy(String name, int size, String kind, boolean medical, Storage storage) {
        this.name = name;
        this.size = size;
        this.kind = kind;
        this.medical = medical;
        this.storage = storage;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int size() {
        // [Virtual Proxy] 원본 데이터를 디스크에서 로드하지 않고 가벼운 메타데이터로 즉시 응답
        return size;
    }

    @Override
    public int count() {
        return 1;
    }

    @Override
    public String open(String role) {
        // [Protection Proxy] 민감한 의료 사진에 대한 역할 기반 접근 제어 (RBAC)
        if (medical && !role.equals("손해사정사")) {
            return "[열람 거절] " + name + " 열람 불가 (" + role + " 권한 없음 - 의료법 및 개인정보보호법 준수)";
        }

        // [Virtual Proxy] 실제 열람/재생 요청 시점에 비로소 무거운 원본 미디어 생성 및 로드
        if (realMedia == null) {
            System.out.println("   (!) [Virtual Proxy] 저장소에서 " + name + " 원본(" + size + "MB) 비동기 로드 실행...");
            if ("사진".equals(kind)) {
                realMedia = new Photo(name, size, storage);
            } else {
                realMedia = new Video(name, size, storage);
            }
        }

        return "[열람 성공] " + realMedia.read() + " (승인 사용자: " + role + ")";
    }
}
