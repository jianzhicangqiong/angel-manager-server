package com.hero.angel.result;

import java.util.Collection;

/**
 * 分页数据Bean
 * 包装分页数据
 */
public class PageBean {

    // 默认初始值，在接收参数为0的时候生效
    private static final int CURRENT_PAGE = 1;
    private static final int PAGE_SIZE = 6;

    // 存放查询结果
    private Collection list;

    // 分页基本数据
    private int currentPage;
    private int pageSize;
    private long total;

    public PageBean() {
    }

    public PageBean(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Collection getList() {
        return list;
    }

    public void setList(Collection list) {
        this.list = list;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}
