package com.official.read.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.official.read.content.Content;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * com.official.read.util
 * Created by ZP on 2017/8/30.
 * description:
 * version: 1.0
 */

public class UpdateNewUtil {

    private Context context;
    public UpdateNewUtil(Context context) {
        this.context = context;
    }

    /**  下载完成   */
    private static final int DOWNLOAD_STATE_SUCCESS = 0;
    /**  正在下载  */
    private static final int DOWNLOAD_STATE_ING = 1;

    private static final String APP_NAME = "als.apk";

    /**  实时下载进度  */
    private int value;

    /**  具体下载路径 */
    private final static String SD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";

    private ProgressDialog dialog;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int state = msg.what;
            switch (state) {
                case DOWNLOAD_STATE_ING: // 正在下载
                    dialog.setProgress(value);
                    break;
                case DOWNLOAD_STATE_SUCCESS: // 下载完成
                    Toaster.makeText("下载完成，正在准备安装...");
                    installApp();
                    break;
            }
        }
    };

    /** 安装App */
    private void installApp() {
        File f = new File(SD_PATH + "Download/", APP_NAME);
        if(!f.exists()) {
            Toaster.makeText("下载失败");
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, Content.FILE_PROVIDER, f);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.parse("file://" + f.toString()), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void showDialog(final String url, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(android.R.drawable.ic_dialog_info);
        builder.setTitle("检测到最新版本");
        builder.setMessage(message);
        builder.setCancelable(false);
        builder.setPositiveButton("马上更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    dialog.dismiss();
                    if (TextUtils.isEmpty(url)) {
                        Toaster.makeText("下载地址为空！");
                    } else {
                        downFile(url);
                    }
                } else {
                    Toaster.makeText("SD卡不可用");
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void downFile(final String downLoadUrl) {
        dialog = new ProgressDialog(context);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setMax(100);
        dialog.setCancelable(false);
        dialog.setTitle("下载");
        dialog.setMessage("正在更新...");
        dialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(downLoadUrl);
                    java.net.HttpURLConnection conn = (java.net.HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(50 * 1000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Charser", "GBK,utf-8;q=0.7,*;q=0.3");
                    int length = conn.getContentLength();
                    InputStream inputStream = conn.getInputStream();
                    File file = new File(SD_PATH + "Download/");
                    // 判断文件目录是否存在
                    if (!file.exists()) {
                        file.mkdir();
                    }
                    File apkFile = new File(SD_PATH + "Download/", APP_NAME);
                    //判断apk文件是否存在
                    if(apkFile.exists()){
                        apkFile.delete();
                    }
                    FileOutputStream out = new FileOutputStream(apkFile);
                    int count = 0;
                    //缓存
                    byte[] b = new byte[1024];
                    do {
                        int numRead = inputStream.read(b);
                        count = count + numRead;
                        //计算进度条的大小
                        value = (int) (((float) count / length) * 100);
                        handler.sendEmptyMessage(DOWNLOAD_STATE_ING);
                        if (numRead <= 0) {//下载完成
                            dialog.dismiss();
                            handler.sendEmptyMessage(DOWNLOAD_STATE_SUCCESS);
                            break;
                        }
                        out.write(b, 0, numRead);
                    } while (true);
                    out.close();
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
