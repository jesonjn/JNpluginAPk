package net.jeson.jnpluginlibrary.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by jiangneng on 6/28/16.
 */
public class FileUtils {
    public static void copyFile(InputStream sourceFile, File destFile) {
        try {
            if (sourceFile.equals(destFile)) {
                return;
            }
            if (!destFile.getParentFile().exists() && !destFile.getParentFile().mkdirs()) {
                throw new IOException("Cannot create directory " + destFile.getParent());
            }
            final int BUFFER = 2048;
            BufferedInputStream source = new BufferedInputStream((sourceFile), BUFFER);
            try {
                BufferedOutputStream dest = new BufferedOutputStream(new FileOutputStream(destFile), BUFFER);
                try {
                    int count;
                    byte data[] = new byte[BUFFER];
                    while ((count = source.read(data, 0, BUFFER)) != -1) {
                        dest.write(data, 0, count);
                    }
                } finally {
                    dest.close();
                }
            } finally {
                source.close();
                ;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
