package service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;

public class TestFile {
    public static void main(String[] args) {
        //为当前位置创建一个file对象
        File file = new File("newFile.txt");

        try {

            //尝试基于该对象创建文件
            boolean value = file.createNewFile();
            if (value) {
                System.out.println("新文件已创建。");
            }
            else {
                System.out.println("该文件已经存在。");
            }
        }
        catch(Exception e) {
            e.getStackTrace();
        }
    }

    }

