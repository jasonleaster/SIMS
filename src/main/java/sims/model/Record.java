package sims.model;

import java.util.Date;

public class Record {

    public class Type{
        public static final String UPLOAD   = "upload";
        public static final String DOWNLOAD = "download";
    }

    public Record(){}

    public Record(Date date, String bookId, String userId, String recordtype) {
        this.id = null; //increase automatically
        this.date = date;
        this.bookId = bookId;
        this.userId = userId;
        this.recordtype = recordtype;
    }

    private Integer id;

    private Date date;

    private String bookId;

    private String userId;

    private String recordtype;

    public Integer getId() {
        return id;
    }

    /*
    Should not supply a setter method for Record#id.
    In database, the primary key(Record#id) will increase automatically.

    public void setId(Integer id) {
        this.id = id;
    }
    */


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId == null ? null : bookId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getRecordtype() {
        return recordtype;
    }

    public void setRecordtype(String recordtype) {
        this.recordtype = recordtype == null ? null : recordtype.trim();
    }
}