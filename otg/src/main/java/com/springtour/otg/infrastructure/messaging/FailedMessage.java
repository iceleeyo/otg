/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.infrastructure.messaging;

/**
 *
 * @author Future
 */
public class FailedMessage {
    /**
     * 
     */
    private Long id;
    /**
     * 消息
     */
    private String message;
    /**
     * 队列名称
     */
    private String qName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getqName() {
        return qName;
    }

    public void setqName(String qName) {
        this.qName = qName;
    }
}
