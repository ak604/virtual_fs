package org.vfs;

import java.io.*;

public class VirtualFile implements FileInterface {

    Inode inode;
    FileInputStream fis;
    FileOutputStream fos;

    VirtualFileSystem vfs;
    public VirtualFile(Inode inode, FileInputStream fis, FileOutputStream fos, VirtualFileSystem vfs){
        this.inode = inode;
        this.fis = fis;
        this.fos =fos;
        this.vfs = vfs;
    }
    @Override
    public InputStream fread() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        for(Block block : inode.getBlocks()){
            byte[]  bytes = fis.readNBytes(block.getWrittenSize());
            os.write(bytes);
        }
        return new ByteArrayInputStream(os.toByteArray());
    }

    @Override
    public void fwrite(InputStream stream) throws IOException {
        while(stream.available() > 0) {
            Block nextBlock = vfs.getNextFreeBlock();
            byte[] bytes = stream.readNBytes(nextBlock.getSize());
            fos.write(bytes, nextBlock.getOffset(), bytes.length);
            nextBlock.setWrittenSize(bytes.length);
            inode.getBlocks().add(nextBlock);
        }
    }
}
