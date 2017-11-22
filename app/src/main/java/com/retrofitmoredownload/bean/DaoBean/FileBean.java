package com.retrofitmoredownload.bean.DaoBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * User: 张亚博
 * Date: 2017-11-21 22:31
 * Description：
 */
@Entity
public class FileBean {
    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "PATH")
    private String path;
    @Property(nameInDb = "CONTENTLENGTH")
    private String contentLength;
    @Property(nameInDb = "THREADID")
    private String ThreadId;
    public String getThreadId() {
        return this.ThreadId;
    }
    public void setThreadId(String ThreadId) {
        this.ThreadId = ThreadId;
    }
    public String getContentLength() {
        return this.contentLength;
    }
    public void setContentLength(String contentLength) {
        this.contentLength = contentLength;
    }
    public String getPath() {
        return this.path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1644617568)
    public FileBean(Long id, String path, String contentLength, String ThreadId) {
        this.id = id;
        this.path = path;
        this.contentLength = contentLength;
        this.ThreadId = ThreadId;
    }
    @Generated(hash = 1910776192)
    public FileBean() {
    }
}
