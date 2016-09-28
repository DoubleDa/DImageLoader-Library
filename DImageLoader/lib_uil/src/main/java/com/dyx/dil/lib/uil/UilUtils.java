package com.dyx.dil.lib.uil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Author: dayongxin(dayongxin@wochu.cn)
 * Date: 2016-09-27
 * Time: 20:01
 * Project:
 * Function:
 * Description：图片加载库uil工具类
 */
public class UilUtils {
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private MemoryCache memoryCache = imageLoader.getMemoryCache();
    private DiskCache diskCache = imageLoader.getDiskCache();

    /**
     * 初始化配置
     *
     * @param context
     */
    public void initConfig(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1025 * 1024)
                .defaultDisplayImageOptions(getDisplayImageOptions())
                .writeDebugLogs()
                .build();
        imageLoader.init(configuration);
    }

    /**
     * 图片显示配置
     *
     * @return
     */
    private DisplayImageOptions getDisplayImageOptions() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_launcher)
                .showImageForEmptyUri(R.drawable.ic_launcher)
                .showImageOnFail(R.drawable.ic_launcher)
                .resetViewBeforeLoading(false)
                .delayBeforeLoading(1000)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .preProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return null;
                    }
                }).postProcessor(new BitmapProcessor() {
                    @Override
                    public Bitmap process(Bitmap bitmap) {
                        return null;
                    }
                })
                .considerExifParams(false)
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .decodingOptions(new BitmapFactory.Options())
                .displayer(new SimpleBitmapDisplayer())
                .handler(new Handler())
                .build();
        return options;
    }

    /**
     * ImageLoader是否被初始化
     *
     * @return
     */
    public boolean isInited() {
        return imageLoader.isInited();
    }

    /**
     * 显示图片
     *
     * @param imgUrl
     * @param imageView
     */
    public void displayImage(String imgUrl, ImageView imageView) {
        imageLoader.displayImage(imgUrl, imageView);
    }

    /**
     * 加载图片
     *
     * @param imgUrl
     */
    public void loadImage(String imgUrl) {
        imageLoader.loadImage(imgUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });
    }

    /**
     * 同步加载图片
     *
     * @param imgUrl
     */
    public void loadImageSync(String imgUrl) {
        imageLoader.loadImageSync(imgUrl);
    }

    /**
     * 清除内存
     */
    public void clearMc() {
        memoryCache.clear();
    }

    /**
     * 从内存读取图片
     *
     * @param imgUrl
     * @return
     */
    public Bitmap getMcBitmap(String imgUrl) {
        return memoryCache.get(imgUrl);
    }

    /**
     * 存入缓存
     *
     * @param imgUrl
     * @param bitmap
     */
    public void putMcBitmap(String imgUrl, Bitmap bitmap) {
        memoryCache.put(imgUrl, bitmap);
    }

    /**
     * 移除图片
     *
     * @param imgUrl
     */
    public void removeMcBitmap(String imgUrl) {
        memoryCache.remove(imgUrl);
    }

    /**
     * 获取图片键值集合
     *
     * @return
     */
    public Collection<String> keysMcBitmap() {
        return memoryCache.keys();
    }

    /**
     * 清除内存缓存
     */
    public void clearMemoryCache() {
        imageLoader.clearMemoryCache();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDiskCache() {
        imageLoader.clearDiskCache();
    }

    /**
     * 清除磁盘缓存
     */
    public void clearDc() {
        diskCache.clear();
    }

    /**
     * 移除磁盘缓存
     *
     * @param imgUrl
     */
    public void removeDc(String imgUrl) {
        diskCache.remove(imgUrl);
    }

    /**
     * 获取磁盘缓存文件
     *
     * @param imgUrl
     * @return
     */
    public File getDcFile(String imgUrl) {
        return diskCache.get(imgUrl);
    }

    /**
     * 关闭磁盘缓存
     */
    public void closeDc() {
        diskCache.close();
    }

    /**
     * 获取磁盘缓存目录
     *
     * @return
     */
    public File getDcDirectory() {
        return diskCache.getDirectory();
    }


    /**
     * 向磁盘保存图片
     *
     * @param imgUrl
     * @param bitmap
     */
    public void saveDc(String imgUrl, Bitmap bitmap) {
        try {
            diskCache.save(imgUrl, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止加载图片
     */
    public void pause() {
        imageLoader.pause();
    }


    /**
     * 恢复加载图片
     */
    public void resume() {
        imageLoader.resume();
    }

    /**
     * 停止加载图片
     */
    public void stop() {
        imageLoader.stop();
    }

    /**
     * 销毁加载图片任务
     */
    public void destroy() {
        imageLoader.destroy();
    }

    /**
     * 获取某个ImageView的图片url
     *
     * @param imageView
     * @return
     */
    public String getLoadingUriForView(ImageView imageView) {
        return imageLoader.getLoadingUriForView(imageView);
    }

    /**
     * 取消显示图片任务
     *
     * @param imageView
     */
    public void cancelDisplayTask(ImageView imageView) {
        imageLoader.cancelDisplayTask(imageView);
    }
}  