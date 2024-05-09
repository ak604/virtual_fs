package org.vfs;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VirtualFileSystem implements FileSystemInterface {

    private File file;

    private int vfsSize ;
    private int blockSize;
    private List<Block> metaDataBlocks;
    private Map<Integer, Boolean> blockMap;
    private List<Block> unusedBlocks;

    Map<String , Inode> fileMap ;
    public VirtualFileSystem(String filePath, int vfsSize, int blockSize) throws FileNotFoundException {

        this.blockSize = blockSize;
        this.vfsSize = vfsSize;
        unusedBlocks = new ArrayList<>();

        file = new File(filePath);

        try(FileOutputStream fos = new FileOutputStream(file)){
            fos.write(new byte[vfsSize]);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int numBlocks = vfsSize/blockSize;
        for(int i =0 ;i< numBlocks ;i++){
            Block b = new Block(i, i*blockSize, blockSize);
            unusedBlocks.add(b);
        }
        blockMap = new HashMap<>();
        //later will reload from file
        fileMap = new HashMap<>();

    }

    public Block getNextFreeBlock(){
        Block block = unusedBlocks.remove(0);
        blockMap.put(block.getId(), true);
        return block;
    }

    @Override
    public VirtualFile fopen(String filePath) {
        Inode inode = fileMap.getOrDefault(filePath, new Inode(filePath, new ArrayList<>()));
        FileInputStream fis;
        FileOutputStream fos;
        try {
             fis = new FileInputStream(file);
             fos = new FileOutputStream(file);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("file not found");
        }
        return new VirtualFile(inode, fis, fos, this);
    }

    @Override
    public void rename(String filePath, String newFilePath) {
        Inode inode = fileMap.get(filePath);
        if(inode != null){
            fileMap.remove(filePath);
            fileMap.put(newFilePath, inode);
        }
    }

    @Override
    public void remove(String filePath) {
        Inode inode = fileMap.get(filePath);
        if(inode != null){

            for(Block block : inode.getBlocks()){
                blockMap.put(block.getId(), false);
                unusedBlocks.add(block);
            }
            fileMap.remove(filePath);
        }
    }

    @Override
    public void fclose(String filePath) {
    }
}
