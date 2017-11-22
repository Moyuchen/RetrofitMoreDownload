package com.retrofitmoredownload;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.retrofitmoredownload.bean.DaoBean.DaoMaster;
import com.retrofitmoredownload.bean.DaoBean.DaoSession;

/**
 * User: 张亚博
 * Date: 2017-11-21 22:34
 * Description：
 */
public class MyApp extends Application {

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDao();
    }

    private void initDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "file.db", null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster master=new DaoMaster(writableDatabase);
        daoSession = master.newSession();



    }
}
