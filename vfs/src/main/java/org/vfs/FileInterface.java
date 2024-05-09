package org.vfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileInterface {

    InputStream fread() throws IOException;

    void fwrite(InputStream stream) throws IOException;




}
