package com.briup.util.Impl;


import java.io.Closeable;
import java.io.IOException;

public class IOUtil {
    public static void close(Closeable... closeableList) {
        try {
            for (Closeable closeable : closeableList) {
                if (closeable != null) {
                    closeable.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
