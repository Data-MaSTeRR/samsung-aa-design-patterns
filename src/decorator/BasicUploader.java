package src.decorator;

/** 가장 기본: 받기만 한다. */
public class BasicUploader implements Uploader {
    @Override
    public String upload() { return "수신"; }
}
