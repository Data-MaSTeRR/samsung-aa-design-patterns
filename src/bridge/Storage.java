package src.bridge;

/**
 * [Bridge - Implementor]
 * 저장소 인터페이스. 고속 스토리지, 장기 보관소 등 다양한 물리적 저장 매체를 추상화.
 */
public interface Storage {
    String save(String name, int size);
    String load(String name);
}
