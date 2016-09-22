package com.godspeed.source.net;

import android.graphics.Color;
import android.widget.ImageView;

import com.godspeed.source.context.GodspeedContext;
import com.godspeed.source.util.collection.CheckUtil;
import com.squareup.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;
import com.squareup.picasso.RequestCreator;
import com.squareup.picasso.Transformation;

/**
 * Created with IntelliJ IDEA. User: liu_yu Date: 13-9-3 Time: 下午2:50 To change
 * this template use File | Settings | File Templates.
 */
public class ImageLoading {

    public static String kBaseURL = "";

    public static String getUrl(String url, int w, int h) {
        if (!CheckUtil.isEmpty(url) && url.startsWith(kBaseURL)) {
            url += getImageSize(w, h);
        }
        return url;
    }

    public static String getImageSize(int width, int height) {
//        return String.format("?imageView2/0/w/%s/h/%s", width, height);  _2e
        return String.format("@%sw_%sh_1e_1c", width, height);

    }
    private final int DEFAULT_MAXSIZE = 400;

    private static ImageLoading imageLoading;

    public synchronized static ImageLoading getInstance() {
        if (imageLoading == null) {
            imageLoading = new ImageLoading();
        }
        return imageLoading;
    }

    public interface ImageLoadCallBack {
        public void callBack(boolean isSuccess);
    }


    /**
     * 异步加载图片
     * 直接设置最大边长度，不做圆角和圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, -1, false, null);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize) {
        downLoadImage(imageView, url, defaultResource, maxSize, -1, false, null);
    }

    /**
     * 异步加载图片
     * 设置最大边长度，做圆角裁剪
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片图片外圆角尺寸
     * @param roundedCornerSize 图片外圆角尺寸
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource, float roundedCornerSize) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, roundedCornerSize, false, null);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, float roundedCornerSize) {
        downLoadImage(imageView, url, defaultResource, maxSize, roundedCornerSize, false, null);
    }

    /**
     * 异步加载图片
     * 设置最大边长度，做圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片图片外圆角尺寸
     * @param isRound         图片是否裁剪成圆形
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource, boolean isRound) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, -1, isRound, null);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, boolean isRound) {
        downLoadImage(imageView, url, defaultResource, maxSize, -1, isRound, null);
    }

    /**
     * 异步加载图片
     * 直接设置最大边长度，不做圆角和圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, -1, false, imageLoadCallBack);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, maxSize, -1, false, imageLoadCallBack);
    }

    /**
     * 异步加载图片
     * 设置最大边长度，做圆角裁剪
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片图片外圆角尺寸
     * @param roundedCornerSize 图片外圆角尺寸
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource, float roundedCornerSize, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, roundedCornerSize, false, imageLoadCallBack);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, float roundedCornerSize, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, maxSize, roundedCornerSize, false, imageLoadCallBack);
    }

    /**
     * 异步加载图片
     * 设置最大边长度，做圆形裁剪
     *
     * @param imageView       图片View
     * @param url             图片地址
     * @param defaultResource 预加载默认图片图片外圆角尺寸
     * @param isRound         图片是否裁剪成圆形
     */
    public void downLoadImage(ImageView imageView, String url, int defaultResource, boolean isRound, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, DEFAULT_MAXSIZE, -1, isRound, imageLoadCallBack);
    }

    public void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, boolean isRound, final ImageLoadCallBack imageLoadCallBack) {
        downLoadImage(imageView, url, defaultResource, maxSize, -1, isRound, imageLoadCallBack);
    }

    /**
     * 异步加载图片
     *
     * @param imageView         图片View
     * @param url               图片地址
     * @param defaultResource   预加载默认图片
     * @param roundedCornerSize 图片外圆角尺寸
     * @param isRound           图片是否裁剪成圆形
     */
    private void downLoadImage(ImageView imageView, String url, int defaultResource, int maxSize, float roundedCornerSize, boolean isRound, final ImageLoadCallBack imageLoadCallBack) {
        if (isRound) {
            downLoadImageRound(imageView, url, defaultResource, maxSize, imageLoadCallBack);
        } else if (roundedCornerSize > 0) {
            downLoadImageRounded(imageView, url, defaultResource, roundedCornerSize, imageLoadCallBack);
        } else {
            downLoadImageMaxSize(imageView, url, defaultResource, maxSize, imageLoadCallBack);
        }
    }

    /**
     * 获取普通图片
     */
    private void downLoadImageMaxSize(ImageView imageView, String url, int defaultResource, int maxSize, final ImageLoadCallBack imageLoadCallBack) {
        clearCache();
        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(defaultResource);
            return;
        }


        RequestCreator picasso = Picasso.with(GodspeedContext.context).load(url)
                .placeholder(defaultResource)
                .error(defaultResource);
//        if (maxSize > 0) {
//            picasso.resize(maxSize, maxSize)
//                    .centerInside();
//        }

        picasso.into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (imageLoadCallBack != null) {
                    imageLoadCallBack.callBack(true);
                }
            }

            @Override
            public void onError() {
                if (imageLoadCallBack != null) {
                    imageLoadCallBack.callBack(false);
                }
            }
        });
    }

    /**
     * 获取圆角图片
     */
    private void downLoadImageRounded(ImageView imageView, String url, int defaultResource, final float roundedCornerSize, final ImageLoadCallBack imageLoadCallBack) {
        clearCache();
        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(defaultResource);
            return;
        }

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(roundedCornerSize)
                .oval(false)
                .build();


        Picasso.with(GodspeedContext.context).load(url)
                .placeholder(defaultResource)
                .error(defaultResource)
                .transform(transformation)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(true);
                        }
                    }

                    @Override
                    public void onError() {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(false);
                        }
                    }
                });
    }

    /**
     * 获取圆图片
     */
    private void downLoadImageRound(ImageView imageView, String url, int defaultResource, int maxSize, final ImageLoadCallBack imageLoadCallBack) {
        clearCache();
        if (CheckUtil.isEmpty(url)) {
            imageView.setImageResource(defaultResource);
            return;
        }

        Transformation transformation = new RoundedTransformationBuilder()
                .borderColor(Color.TRANSPARENT)
                .borderWidthDp(0)
                .cornerRadiusDp(300)
                .oval(false)
                .build();


        Picasso.with(GodspeedContext.context).load(url)
                .placeholder(defaultResource)
                .error(defaultResource)
                .resize(150, 150)
                .transform(transformation)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(true);
                        }
                    }

                    @Override
                    public void onError() {
                        if (imageLoadCallBack != null) {
                            imageLoadCallBack.callBack(false);
                        }
                    }
                });
    }

    private void clearCache() {
        if (PicassoTools.getCacheSize(Picasso.with(GodspeedContext.context)) > 10000000) {
            PicassoTools.clearCache(Picasso.with(GodspeedContext.context));
        }
    }
}
