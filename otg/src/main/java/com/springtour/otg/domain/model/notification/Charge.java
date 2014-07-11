/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springtour.otg.domain.model.notification;


/**
 * 交易扣款结果 
 */
public enum Charge {

    /**
     * 扣款成功
     */
    SUCCESS("1","扣款成功"),
    /**
     * 扣款失败
     */
    FAILURE("0","扣款失败");

    public static Charge of(String code) {
        Charge result = null;
        for (Charge type : Charge.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return result;
    }
    /**
     * 代码
     */
    private final String code;
    private final String name;


    private Charge(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    
    public String getName(){
        return name;
    }

    public boolean sameCodeAs(String code) {
        return code != null && this.code.equals(code);
    }
    
     public static String getNameFromCode(String code){
           for(Charge charge : Charge.values()){
               if(charge.sameCodeAs(code)){
                   return charge.getName();
               }
           }
           return "";
        }
}
