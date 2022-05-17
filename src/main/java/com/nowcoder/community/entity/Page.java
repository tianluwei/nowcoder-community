package com.nowcoder.community.entity;

public class Page {
    private int current = 1;
    private int limit = 10;
    //    数据总条数
    private int rows;

    //    查询路径，分页链接
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >= 0) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //    获取当前页起始行
    public int getOffset() {
//        System.out.println("我起始行又被查了一次哦！！！");
        return (current - 1) * limit;
    }

    //    获取总页数
    public int getTotal() {
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    //    获取起始页码
    public int getFrom() {
        if (current - 2 > 1) {
            return current - 2;
        } else {
            return 1;
        }
    }

    //    获取结束页码
    public int getTo() {
        int total = getTotal();
        return current + 2 > total ? total : current + 2;
    }
}
