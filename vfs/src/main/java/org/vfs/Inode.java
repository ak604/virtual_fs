package org.vfs;

import java.util.List;

public class Inode {
    public String getFileName() {
        return fileName;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    private String fileName;
    private List<Block> blocks;

    public Inode(String fileName, List<Block> blocks) {
        this.fileName = fileName;
        this.blocks = blocks;
    }
}
