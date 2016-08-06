package com.gxu.smallshop.utils;

import android.os.Environment;
import android.renderscript.ScriptIntrinsicYuvToRGB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import javax.sql.StatementEvent;

/**
 * Created by Administrator on 2015.
 */
public class CommonUtils {

    public static String saveFileToSdcard(String fileName, byte[] data) {
        String path="";
        boolean flag = false;
        // 先判断sdcard的状态是否可用
        String state = Environment.getExternalStorageState();
        FileOutputStream outputStream = null;
        // 获得srcard的根路径 /mnt/sdcard/....
        File root = Environment.getExternalStorageDirectory();
        //SD卡正常挂载
        if (state.equals(Environment.MEDIA_MOUNTED)) {

            File file = new File(root.getAbsolutePath() + "/backup");
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                outputStream = new FileOutputStream(new File(file, fileName));
                outputStream.write(data, 0, data.length);
                flag = true;
                path = file.getAbsolutePath();
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            } finally {
                if (outputStream != null) {
                    try {
                        outputStream.close();
                    } catch (Exception e2) {
                        // TODO: handle exception
                        e2.printStackTrace();
                    }

                }
            }

        }
        return path;
    }

    public static byte[]  ChangeListToByte(List<String> list) {
        String listStr="";
        byte[] bytes=new byte[]{};
        for (String str: list) {
            listStr=listStr+str;

        }
        bytes= listStr.getBytes();
        return bytes;
    }
}
