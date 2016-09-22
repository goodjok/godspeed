package com.godspeed.source.util.io;

import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;
import android.os.Environment;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.util.system.DateUtil;
import com.godspeed.source.util.system.DeviceUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/**
 * @author Nick create at 2011-3-18
 */
public class StorageUtil {

    public static final String ROOT_ASSET = "/android_asset/";

    public static boolean isSdcardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    //应用临时使用的缓存目录（每次使用后应删除）
    public static String getCache() {
        return GodspeedContext.context.getCacheDir() + "/" + StorageUtil.getPackageName();
    }

    public static String getCacheFileName() {
        return GodspeedContext.context.getCacheDir() + "/" + StorageUtil.getPackageName() + "-" + System.currentTimeMillis();
    }

    //获取应用根目录
    public static String getStorageDirectory() {
        if (hasSdcard())
            return Environment.getExternalStorageDirectory() + "/" + StorageUtil.getPackageName();
        else
            return Environment.getDataDirectory() + "/" + StorageUtil.getPackageName();
    }

    public static boolean hasSdcard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    //获取SD卡目录
    public static String getStorageDirectory(String filePath) {
        return Environment.getExternalStorageDirectory() + "/" + filePath;
    }

    //获取App中图片存储目录
    public static String getAppImageDir() {
        return getStorageDirectory() + "/ImagesCache/";
    }


    //获得上传统计日志的文件名称
    public static String getStatUploadFilePath() {
        String stbId = DeviceUtil.getUDID();
        stbId = stbId.replaceAll(":", "");
        return StorageUtil.getStorageDirectory() + "/Temp/" + stbId + "-" + DateUtil.getInstance().getTimeInMillis();
    }

    public static String getStorage(String content, long id, boolean isDirectory) {
        String external = Environment.getExternalStorageDirectory().getPath();

        external += "/." + StorageUtil.getPackageName() + "/Content/" + content + id;

        if (isDirectory) {
            external += "/";
        }

        return external;
    }

    public static String getImagePath(String destName, boolean isDirectory) {
        String external = Environment.getExternalStorageDirectory().getPath();
        external += "/" + StorageUtil.getPackageName() + "/Content/Images/" + destName;
        if (isDirectory) {
            external += "/";
        }
        //为适应华为机顶盒内置SD卡无法访问的情况，将图片存储到应用Cache中
//        String external = Deeper.context.getCacheDir().getPath();
//        external += "/" + "cover_image_cache" + "/" + destName;
//        if (isDirectory) {
//            external += "/";
//        }

        return external;
    }

    public static void cleanAll() {
        try {
            if (new File(GodspeedContext.context.getCacheDir() + "/").exists())
                FileUtils.cleanDirectory(new File(GodspeedContext.context.getCacheDir() + "/"));
            cleanRootDirectory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cleanRootDirectory() {
        try {

            String external = getStorageDirectory();
            File file = new File(external);
            if (file.exists())
                FileUtils.cleanDirectory(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void cleanCacheDir() {
        try {
            if (new File(GodspeedContext.context.getCacheDir() + "/").exists())
                FileUtils.cleanDirectory(new File(GodspeedContext.context.getCacheDir() + "/"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除指定目录下的所有文件
     *
     * @param path 指定目录
     */
    public static void cleanDataCache(String path) {
        try {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                FileUtils.cleanDirectory(file);
            } else {
                LogUtil.d("Dir is not exist or Dir is not Directory! Dir Path: " + path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static String[] listFiles(String path, String suffix) {
        if (path.startsWith(ROOT_ASSET)) {
            if (path.endsWith("/")) {
                path = path.substring(0, path.length() - 1);
            }

            AssetManager assetManager = GodspeedContext.context.getAssets();

            String[] files = new String[0];

            try {
                files = assetManager.list(path.substring(ROOT_ASSET.length()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            ArrayList<String> result = new ArrayList<String>();

            for (int i = 0; i < files.length; i++) {
                if (files[i].endsWith(suffix)) {
                    result.add(path + "/" + files[i]);
                }
            }

            return result.toArray(new String[result.size()]);
        } else {

            ArrayList<String> result = new ArrayList<String>();
            Collection<File> files = FileUtils.listFiles(new File(path), suffix);
            for (File file : files) {
                if (!file.getName().startsWith(".")) {
                    result.add(file.getAbsolutePath());
                }
            }
            //对图片地址按照文件名称(数字)进行排序（升序）
            String[] picPathArray = result.toArray(new String[result.size()]);
            Arrays.sort(picPathArray, new Comparator<Object>() {
                public int compare(Object o1, Object o2) {
                    String path1 = (String) o1;
                    String path2 = (String) o2;
                    long pic1NameNum = getPicNameNm(path1);
                    long pic2NameNum = getPicNameNm(path1);
                    if (pic1NameNum > pic2NameNum) {
                        return -1;
                    } else if (pic1NameNum < pic2NameNum) {
                        return 1;
                    } else {
                        return path1.compareTo(path2);
                    }
                }

                private Long getPicNameNm(String path1) {
                    try {
                        int nameStartIndex = path1.lastIndexOf("/");
                        int nameEndIndex = path1.lastIndexOf(".");
                        String fileName = path1.substring(nameStartIndex + 1, nameEndIndex);
                        return Long.parseLong(fileName);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    return -1l;
                }
            });

            return picPathArray;
        }
    }

    public static InputStream openInputStream(String file) throws IOException {
        if (file.startsWith(ROOT_ASSET)) {
            AssetManager assetManager = GodspeedContext.context.getAssets();
            return assetManager.open(file.substring(ROOT_ASSET.length()));
        } else {
            return FileUtils.openInputStream(new File(file));
        }
    }

    public static boolean deleteFile(String file) {
        if (!file.startsWith(ROOT_ASSET)) {
            return FileUtils.deleteQuietly(new File(file));
        }

        return true;
    }

    public static boolean deleteDirectory(String file) throws IOException {
        if (!file.startsWith(ROOT_ASSET)) {
            FileUtils.forceDelete(new File(file));
        }

        return true;
    }

    public static void cleanCache() throws IOException {
        FileUtils.cleanDirectory(GodspeedContext.context.getCacheDir());
    }

    public static String getPackageName() {
        ApplicationInfo info = GodspeedContext.context.getApplicationInfo();
        return info.packageName;
    }

}
