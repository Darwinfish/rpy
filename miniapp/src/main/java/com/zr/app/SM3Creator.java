package com.zr.app;

import com.hundsun.fund.comm.app.common.utils.FileUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhengzr14796 on 2016/3/14.
 */
public class SM3Creator {
    public static void main(String[] args){
        System.out.println("请输入要签名的文件路径，按回车结束：");
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        String filePath=null;
        try {
            if ((filePath=br.readLine())!=null)
            {
                System.out.println(filePath);
                create(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    private static void create(String filePath) {
        File[] downloadFiles = new File(filePath).listFiles();
        for (File file : downloadFiles){
            SM3 sm3 = new SM3(false, true, false);
            byte[] sm3byte = new byte[0];
            try {
                sm3byte = sm3.getFileHash(file.getAbsolutePath());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String sm3digest = sm3.byte2hex(sm3byte);

            if (saveSM3File(file.getName(),sm3digest,filePath)){
                System.out.println("成功");
            }

        }
    }

    private static boolean saveSM3File(String fileName, String filedigest,String filePath) {
        String SM3FilePath = filePath+"sm3";
        if (!FileUtil.exists(SM3FilePath)) {
            FileUtil.mkdir(SM3FilePath);
        }
        String sm3FileName = String.format("%s/%s",SM3FilePath,fileName);
        FileUtil.createFile(sm3FileName);
        FileUtil.writeFile(sm3FileName,filedigest,"utf-8");
        return true;
    }
}
