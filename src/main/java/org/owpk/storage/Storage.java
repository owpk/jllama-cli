package org.owpk.storage;

import java.util.List;

public interface Storage {
    byte[] getContent(String path) throws StorageException;

    byte[] getContent(String path, String... more) throws StorageException;

    List<Content> getContents(String path) throws StorageException;

    List<Content> getContents(String path, String... more) throws StorageException;

    void saveContents(List<Content> contents) throws StorageException;

    boolean exists(String path);

    boolean exists(String path, String... more);

    String createFileOrDirIfNotExists(boolean isDir, String path);

    String createFileOrDirIfNotExists(boolean isDir, String path, String... more);

    boolean saveContent(String path, byte[] composedArray, boolean append) throws StorageException;
}
