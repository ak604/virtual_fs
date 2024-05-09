package org.example;

import org.vfs.VirtualFile;
import org.vfs.VirtualFileSystem;

import java.io.*;

public class Main {


    public static void main(String[] args) {
        VirtualFileSystem vfs;

        {
            try {
                vfs = new VirtualFileSystem("vfsfile", 100000, 100);
                VirtualFile vf = vfs.fopen("first");
                String str = "some text";
                InputStream is = new ByteArrayInputStream(str.getBytes());
                vf.fwrite(is);

                InputStream is2 = vf.fread();
                System.out.println(new String(is2.readAllBytes()));

            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


}