package src.bridge;

/**
 * [Bridge - ConcreteImplementor]
 * 고속 스토리지 (SSD / Hot Tier). 최근 조사 중인 미디어 고속 입출력에 사용.
 */
public class HotStorage implements Storage {
    @Override
    public String save(String name, int size) {
        return "고속저장소(SSD)에 [" + name + " (" + size + "MB)] 영구 저장 완료";
    }

    @Override
    public String load(String name) {
        return "고속저장소(SSD)에서 [" + name + "] 고속 스트리밍";
    }
}
