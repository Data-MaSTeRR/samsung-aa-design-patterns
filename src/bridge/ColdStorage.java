package src.bridge;

public class ColdStorage implements Storage {
    @Override
    public String save(String fileName) { return "보관저장소에 " + fileName + " 저장"; }
}
