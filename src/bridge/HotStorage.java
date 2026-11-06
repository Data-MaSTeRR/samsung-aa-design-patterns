package src.bridge;

public class HotStorage implements Storage {
    @Override
    public String save(String fileName) { return "고속저장소에 " + fileName + " 저장"; }
}
