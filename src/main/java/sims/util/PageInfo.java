package sims.util;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "rawtypes"})
public class PageInfo {
    public static final int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE;

	private long start; //The offset of the first data item in this page.

	private List data; // We put the data in this page into the list @data

    private long totalCount; // The total item in the database table

    private long currentPageNum;

    private String URL;

    public PageInfo(){
        this(0, DEFAULT_PAGE_SIZE, new ArrayList());
    }

    public PageInfo(long start, int pageSize, List data){
        this.start = start;
        this.pageSize = pageSize;
        this.totalCount = -1;
        this.data = data;
        this.URL  = "/";
        this.currentPageNum = start / pageSize + 1;
    }

    public long getTotalPageCount(){
        if(totalCount % pageSize == 0){
            return totalCount / pageSize;
        }else {
            return totalCount / pageSize + 1;
        }
    }

    public long getCurrentPageNum(){
        return currentPageNum;
    }

    public boolean isHasNextPage(){
        return getCurrentPageNum() < getTotalPageCount();
    }

    public boolean isHasPrevPage(){
        return getCurrentPageNum() > 1;
    }

    protected static int getStartIdxOfThePage(int pageNum){
        return getStartIdxOfThePage(pageNum, DEFAULT_PAGE_SIZE);
    }

    public static int getStartIdxOfThePage(int pageNum, int pageSize){
        return (pageNum - 1) * pageSize;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
