package com.nowcoder.community.entity;

import java.util.Date;

public class LoginTicket {
    private int id;
    private int userId;

//    todo 这个ticket是啥，我怎么忘了？？？
    private String ticket;
    //    0表示有效，1表示失效
    private int status;
    private Date expired;

    public LoginTicket() {
    }

    public LoginTicket(int id, int userId, String ticket, int status, Date expired) {
        this.id = id;
        this.userId = userId;
        this.ticket = ticket;
        this.status = status;
        this.expired = expired;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    @Override
    public String toString() {
        return "LoginTicket{" +
                "id=" + id +
                ", userId=" + userId +
                ", ticket='" + ticket + '\'' +
                ", status=" + status +
                ", expired=" + expired +
                '}';
    }
}
