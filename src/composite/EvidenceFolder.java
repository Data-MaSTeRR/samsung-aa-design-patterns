package src.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * [Composite - Composite]
 * 사고, 조사 회차, 증빙 묶음 등 하위 증빙(Evidence)들을 포함하는 폴더.
 */
public class EvidenceFolder implements Evidence {
    private final String name;
    private final List<Evidence> children = new ArrayList<>();

    public EvidenceFolder(String name) {
        this.name = name;
    }

    public EvidenceFolder add(Evidence item) {
        children.add(item);
        return this;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int size() {
        int total = 0;
        for (Evidence child : children) {
            total += child.size();
        }
        return total;
    }

    @Override
    public int count() {
        int total = 0;
        for (Evidence child : children) {
            total += child.count();
        }
        return total;
    }

    @Override
    public String open(String role) {
        return "[" + name + " 폴더]는 직접 열람할 수 없습니다. 개별 미디어 파일을 지정해주세요.";
    }

    /**
     * 폴더 트리 하위에서 미디어를 찾아 열람 요청을 전달.
     */
    public String openMedia(String fileName, String role) {
        for (Evidence child : children) {
            if (child.name().equals(fileName)) {
                return child.open(role);
            }
            if (child instanceof EvidenceFolder folder) {
                String result = folder.openMedia(fileName, role);
                if (!result.startsWith("[파일 미발견]")) {
                    return result;
                }
            }
        }
        return "[파일 미발견] " + fileName;
    }
}
