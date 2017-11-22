package com.retrofitmoredownload.net;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by yfeng on 2017/11/9.
 */

public class DownloadThread extends Thread{
    private String downloadUrl = "";
    private String path;
    private int threadNum = 5;
    private String TAG="=======DownloadThread=======";

    private boolean  isDown;
    private Context context;
    String range=null;

    private int[] LENG={0};


    public void setDown(boolean down) {
        isDown = down;
    }

    public DownloadThread(String downloadUrl, String path,Context context) {
        this.downloadUrl = downloadUrl;
        this.path = path;
        this.context=context;
    }

    @Override
    public void run() {

        RetrofitUtils.download(RequestApi.BASE_URL2).getFileLenght().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                final long l = response.body().contentLength();
                downloadTask(Long.valueOf(l));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    @SuppressLint("LongLogTag")
    public void downloadTask(final long length){

        final File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //例如  1000kb  3   333
        long blockSize = length/threadNum;

        //计算出下载块以后   创建线程执行下载操作
        for (int i = 0; i < threadNum; i++) {

            //计算开始位置
            final long[] startPosition = {blockSize * i};
            //让最后一个线程下载的大小是正好的，  总长度 - 除了最后一个块的大小和
            if(i == threadNum - 1){
                blockSize = length - blockSize * (threadNum - 1);
            }

//            //获取到保存数据库中的下载长度
//            List<FileBean> query = DaoUtils.getInstance(context).Query(downloadUrl,String.valueOf(i));

//            if (query!=null&query.size()>0) {
//                FileBean fileBean = query.get(0);
//                String contentLength = fileBean.getContentLength();
//              //下载头
//                startPos = startPosition[0] + Integer.parseInt(contentLength);
//                range = "bytes=" + (startPosition[0]+Integer.parseInt(contentLength)) + "-" + (startPosition[0] + blockSize - 1);
//
//            }
//            else {
//                DaoUtils.getInstance(context).Insert(downloadUrl,String.valueOf(i),String.valueOf(0));
                range = "bytes=" +startPosition[0] + "-" +(startPosition[0] + blockSize - 1);


//            }



//            final int finalI = i;
//            final int finalI1 = i;
            RetrofitUtils.download(RequestApi.BASE_URL2).downloadFile(range).
                    subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(new Observer<ResponseBody>() {


                @Override
                public void onCompleted() {
                    Log.i(TAG, "onCompleted: ");
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    Log.i(TAG, "onError: e:"+e.toString());
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    RandomAccessFile raf=null;
                    BufferedInputStream bis=null;
                    try {
                            bis = new BufferedInputStream(responseBody.byteStream());
                            raf = new RandomAccessFile(file, "rwd");

                            raf.seek(startPosition[0]);
                            byte[] buff = new byte[1024 * 8];
                            int len = 0;
                            while ((len = bis.read(buff)) != -1) {

                                if (isDown) {
                                    raf.write(buff, 0, len);
//                                    LENG[0] +=len;

                                    rP.retrunProgre(len,length);

                                    //更新数据库中的数据
//                                    DaoUtils.getInstance(context).Update(String.valueOf(finalI),String.valueOf(LENG[0]),downloadUrl);
                                }else {
                                        break;
                                }
                            }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if (raf!=null) {
                            try {
                                raf.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        if (bis!=null) {
                            try {
                                bis.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            });
        }
    }

    public void setThreadNum(int threadNum){
        this.threadNum = threadNum;
    }
    private ReturnProgress rP;

    public void setrP(ReturnProgress rP) {
        this.rP = rP;
    }

    public interface ReturnProgress{
        void retrunProgre(long len,long length);
    }
}
