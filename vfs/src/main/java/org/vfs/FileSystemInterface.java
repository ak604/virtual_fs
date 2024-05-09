package org.vfs;

public interface FileSystemInterface {
    VirtualFile fopen(String filePath);

    void rename(String filePath, String newFilePath);
    void remove(String filePath);

    void fclose(String filePath);
}
