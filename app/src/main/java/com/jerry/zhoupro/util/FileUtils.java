package com.jerry.zhoupro.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

/**
 * Created by wzl-pc on 2016/3/27.
 * FileUtils工具类：文件管理读取
 */
public class FileUtils {

    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 获取文件名称
     *
     * @param filePath
     * @return
     */
    private static String getFileName(String filePath) {
        return filePath.substring(filePath.lastIndexOf("/")).substring(1);
    }

    /**
     * 获取文件的目录
     *
     * @param filePath
     * @return
     */
    private static String getFilePath(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf(File.separator));
    }

    /**
     * 创建文件
     *
     * @param filePath
     * @return
     */
    public static boolean createFile(String filePath) {
        String dirPath = getFilePath(filePath);
        String fileName = getFileName(filePath);
        return createFile(dirPath, fileName);
    }

    /**
     * 创建文件
     *
     * @param dirPath  文件路径
     * @param filename 文件名
     * @return
     */
    private static boolean createFile(String dirPath, String filename) {
        File dir = new File(dirPath);
        // 按照指定的路径创建文件夹
        if (dir.exists() || dir.mkdirs()) {
            File file = new File(dirPath + File.separator + filename);
            if (!file.exists()) {
                // 创建新文件
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dirPath
     */
    public static boolean creatDir(String dirPath) {
        File file = new File(dirPath);
        // 按照指定的路径创建文件夹
        return file.exists() || file.mkdirs();
    }

    /**
     * 删除指定路径文件夹
     *
     * @param dirPath
     */
    public static boolean deleteDir(String dirPath) {
        File file = new File(dirPath);
        // 判断文件夹是否存在
        //删除
        return !(file.exists() && file.isDirectory()) || file.delete();
    }

    /**
     * 删除指定路径文件
     *
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        // 判断文件是否存在
        //删除
        return !file.exists() || file.delete();
    }

    /**
     * 删除指定目录下的所有文件
     *
     * @param dir
     */
    public static void deleteFiles(final File dir) {
        if (!dir.exists()) { return; }
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                deleteFiles(file);
            }
        } else {
            dir.delete();
        }
    }

    /**
     * 将一个InputStream里面的数据写入到文件中
     *
     * @param input
     * @param path
     * @return
     */
    public static void saveFile(InputStream input, String path) {
        OutputStream output = null;
        try {
            if (createFile(path)) {
                File file = new File(path);
                output = new FileOutputStream(file);
                byte buffer[] = new byte[4 * 1024];
                while ((input.read(buffer)) != -1) {
                    output.write(buffer);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将数据写入到缓存
     *
     * @param path
     * @param data
     * @throws IOException
     */
    public static void writeToCache(String path, byte[] data) throws IOException {
        OutputStream os = null;
        try {
            os = new FileOutputStream(path);
            os.write(data);
        } catch (IOException e) {
            Mlog.w(TAG, "file cache(" + path + ") error!");
        } finally {
            if (null != os) { os.close(); }
        }
    }

    /**
     * 文件重命名
     *
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean renameTo(String oldPath, String newPath) {
        boolean renameFlg = false;
        File ofile = new File(oldPath);
        if (ofile.exists()) {
            File nfile = new File(newPath);
            renameFlg = ofile.renameTo(nfile);
        }
        return renameFlg;
    }

    /**
     * 判断文件是否为图片格式(png,jpg)
     *
     * @param path
     * @return
     */
    public static boolean isImgFile(String path) {
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1,
                path.length()).toLowerCase();
        return fileEnd.equals("png") || fileEnd.equals("jpg");
    }

    /**
     * 判断文件是否为pdf格式(pdf)
     *
     * @param path
     * @return
     */
    public static boolean isPdfFile(String path) {
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1,
                path.length()).toLowerCase();
        return fileEnd.equals("pdf");
    }

    /**
     * 判断文件是否为Vedio格式(mp4,3gp)
     *
     * @param path
     * @return
     */
    public static boolean isVideoFile(String path) {
        // 获取扩展名
        String fileEnd = path.substring(path.lastIndexOf(".") + 1,
                path.length()).toLowerCase();
        return fileEnd.equals("mp4") || fileEnd.equals("3gp");
    }

    public static Bitmap getLocalBitmap(String pathString) {
        Bitmap bitmap = null;
        File file = new File(pathString);
        if (file.exists()) {
            bitmap = BitmapFactory.decodeFile(pathString);
        }
        return bitmap;
    }

    /**
     * 生成一个图片的名称
     *
     * @param rootUrl
     * @param date
     * @param imgId
     * @param imgInfo
     * @return
     */
    public static String generyImgUrl(Object rootUrl, Object date, Object imgId, String imgInfo) {
        StringBuilder bf = new StringBuilder();
        try {
            String ext = imgInfo.substring(imgInfo.lastIndexOf(".") + 1,
                    imgInfo.length()).toLowerCase();
            bf.append(rootUrl).append("/");
            bf.append(date).append("/");
            bf.append(imgId).append(ext);
        } catch (Exception e) {
            bf.append("");
        }
        return bf.toString();
    }

    public static Uri saveLocalBitmap(final Bitmap bitmap, final String path) {
        File img = new File(path);
        if (!createFile(path)) { return null; }
        try {
            FileOutputStream fos = new FileOutputStream(img);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.fromFile(img);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            if (fileList != null) {
                for (File aFileList : fileList) {
                    if (aFileList.isDirectory()) {
                        size = size + getFolderSize(aFileList);
                    } else {
                        size = size + aFileList.length();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }


    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file
     * @return
     */
    public static String getFormatSize(File file) {
        return getFormatSize(getFolderSize(file));
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }
}
