package org.vfs;

public class Block {

    private int id;

    private int offset;
    private int size;

    public int getWrittenSize() {
        return writtenSize;
    }

    public void setWrittenSize(int writtenSize) {
        this.writtenSize = writtenSize;
    }

    private int writtenSize;


    public Block(int id, int offset, int size) {
        this.id = id;
        this.offset = offset;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getOffset() {
        return offset;
    }

    public int getSize() {
        return size;
    }


}
