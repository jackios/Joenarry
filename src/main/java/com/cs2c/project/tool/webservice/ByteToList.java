package com.cs2c.project.tool.webservice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;

public class ByteToList {
    /**
     * 功能说明:将byte[]数据解压成List对象于上面过程逆向
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static List uncompressList(byte[] data) throws IOException {
        List result = null;

        ByteArrayInputStream i = new ByteArrayInputStream(data);

        GZIPInputStream gzin = new GZIPInputStream(i);

        ObjectInputStream in = new ObjectInputStream(gzin);

        try {
            result = (List) in.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        i.close();
        gzin.close();
        in.close();
        return result;
    }
}
