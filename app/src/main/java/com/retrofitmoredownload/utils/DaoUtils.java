package com.retrofitmoredownload.utils;

import android.content.Context;

import com.retrofitmoredownload.MyApp;
import com.retrofitmoredownload.bean.DaoBean.FileBean;
import com.retrofitmoredownload.bean.DaoBean.FileBeanDao;

import java.util.List;

/**
 * User: 张亚博
 * Date: 2017-11-22 09:09
 * Description：
 */
public class DaoUtils {

    private final MyApp myApp;
    private static DaoUtils utils;

    private DaoUtils(Context context) {
        myApp = (MyApp) context.getApplicationContext();
    }

    public static DaoUtils getInstance(Context context){
        if (utils==null) {
            synchronized (DaoUtils.class){
                if (utils==null) {
                    utils=new DaoUtils(context);
                }
            }
        }
       return utils;

    }

    /**
     *
     * 保存数据库数据
     * @param downPath
     * @param threadid
     * @param contentlength
     */
    public void Insert(String downPath,String threadid,String contentlength){
        FileBeanDao fileBeanDao = myApp.getDaoSession().getFileBeanDao();
         fileBeanDao.insert(new FileBean(null,downPath,contentlength,threadid));
    }

    /**
     * 格局下载地址删除数据库数据
     * @param downPath
     */
    public void Delete(String downPath){
        FileBeanDao fileBeanDao = myApp.getDaoSession().getFileBeanDao();
        List<FileBean> list = fileBeanDao.queryBuilder().where(FileBeanDao.Properties.Path.eq(downPath)).build().list();
        for (FileBean fileBean : list) {
            Long id = fileBean.getId();
            fileBeanDao.deleteByKey(id);
        }

    }

    /**
     * 更新数据库指定的数据信息
     * @param threadid
     * @param contentlength
     * @param downpath
     */
    public void Update(String threadid,String contentlength,String downpath){
        List<FileBean> list = myApp.getDaoSession().getFileBeanDao().queryBuilder().where(FileBeanDao.Properties.Path.eq(downpath), FileBeanDao.Properties.ThreadId.eq(threadid)).build().list();
        for (FileBean fileBean : list) {
            Long id = fileBean.getId();
            myApp.getDaoSession().getFileBeanDao().update(new FileBean(id,downpath,contentlength,threadid));
        }

    }

    /**
     * 查询数据
     * @param path
     * @return
     */
    public List<FileBean> Query(String path,String threadid){
        List<FileBean> list = myApp.getDaoSession().getFileBeanDao().queryBuilder().where(FileBeanDao.Properties.Path.eq(path), FileBeanDao.Properties.ThreadId.eq(threadid)).build().list();
        return list;
    }
}
