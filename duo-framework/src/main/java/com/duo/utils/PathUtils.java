package com.duo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * <pre>
 * PathUtils 是一个工具类，其中的 generateFilePath(String fileName) 方法用于生成文件路径。
 * 它根据当前日期生成日期路径，并使用 UUID 作为文件名的一部分，最后拼接文件后缀，从而生成一个唯一的文件路径。
 * 该方法在文件上传等场景中可以用于生成文件的保存路径，以确保文件名的唯一性和更好的文件管理
 * </pre>
 *
 * @author <a href="https://github.com/HCDUO">HCDUO</a>
 * @date:2023/7/29 17:12
 */
public class PathUtils {

    public static String generateFilePath(String fileName){
        //根据日期生成路径   2022/1/15/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        String datePath = sdf.format(new Date());
        //uuid作为文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //后缀和文件后缀一致
        int index = fileName.lastIndexOf(".");
        // test.jpg -> .jpg
        String fileType = fileName.substring(index);
        return new StringBuilder().append(datePath).append(uuid).append(fileType).toString();
    }
}
