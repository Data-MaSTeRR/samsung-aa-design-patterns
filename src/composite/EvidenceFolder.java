package src.composite;

import java.util.ArrayList;
import java.util.List;

/** 폴더 (사고, 조사 회차, 증빙 묶음). 안에 파일도, 다른 폴더도 담을 수 있다. */
public class EvidenceFolder implements Evidence {
    private final String name;
    private final List<Evidence> children = new ArrayList<>();

    public EvidenceFolder(String name) {
        this.name = name;
    }

    public EvidenceFolder add(Evidence e) {
        children.add(e);
        return this;
    }

    public int size() {
        int sum = 0;
        for (Evidence e : children) sum += e.size();
        return sum;
    }

    public int count() {
        int sum = 0;
        for (Evidence e : children) sum += e.count();
        return sum;
    }
}
