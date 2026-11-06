package src.bridge;

/**
 * [Bridge - ConcreteImplementor]
 * 장기 보관 저장소 (Cloud Archive / Cold Tier). 종결 건 및 대용량 보관용.
 */
public class ColdStorage implements Storage {
    @Override
    public String save(String name, int size) {
        return "보관저장소(Cloud Archive)에 [" + name + " (" + size + "MB)] 백업 완료";
    }

    @Override
    public String load(String name) {
        return "보관저장소(Cloud Archive)에서 [" + name + "] 원본 아카이브 로드";
    }
}
