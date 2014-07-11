package com.springtour.otg.domain.model.merchant;

import org.apache.commons.lang.builder.StandardToStringStyle;

import com.springtour.otg.domain.model.channel.Channel;
import com.springtour.shared.ValueObject;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 商户
 * 
 * @author Hippoom
 * 
 */
public class Merchant {

    /**
     * 商户名
     */
    private String name;
    /**
     * 商户号
     */
    private String code;
    /**
     * 支付渠道
     */
    private Channel channel;
    /**
     * 商户所属组织
     */
    private String orgId;
    /**
     * 商户所属组织名称
     */
    private String holder;
    /**
     * 商户密钥
     */
    private String key;
    /**
     * 状态
     */
    private String state;

    /**
     * 
     * @param name
     * @param channel
     * @param orgId
     * @param code
     * @param key
     */
    public Merchant(String name, Channel channel, String orgId, String code,
            String key) {
        this.name = name;
        this.code = code;
        this.channel = channel;
        this.orgId = orgId;
        this.key = key;
        this.state = State.ENABLED.getCode();
    }
    
    public boolean isEnabled() {
        return Merchant.State.ENABLED.getCode().equals(this.state);
    }
    
    public void updateName(String name) {
        this.name = name;
    }
    
    public void updateCode(String code) {
        this.code = code;
    }
    
    public void updateKey(String key) {
        this.key = key;
    }
    
    public void switchState(boolean enabled) {
        if (enabled) {
            this.state = State.ENABLED.getCode();
        } else {
            this.state = State.DISABLED.getCode();
        }
    }

    /**
     * 交易状态
     * 
     * @author Hippoom
     * 
     */
    public enum State implements ValueObject<State> {

        /**
         * 可用
         */
        ENABLED("1"),
        /**
         * 不可用
         */
        DISABLED("0");
        /**
         * 代码
         */
        private final String code;
        
        private State(String code) {
            this.code = code;
        }
        
        public String getCode() {
            return code;
        }
        
        @Override
        public boolean sameValueAs(final State other) {
            return other != null && this.code.equals(other.getCode());
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(id).append(code).append(channel).append(orgId).toString();
    }
    
    public String getMerchantStateName() {
        if (this.isEnabled()) {
            return "可用";
        } else {
            return "不可用";
        }
    }
    /**
     * auto-generated surrogate key;
     */
    private Long id;
    /**
     * optimistic lock version
     */
    private int version = 1;

    /**
     * for persistence
     */
    public Merchant() {
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public Channel getChannel() {
        return channel;
    }
    
    public void setChannel(Channel channel) {
        this.channel = channel;
    }
    
    public String getOrgId() {
        return orgId;
    }
    
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    public String getHolder() {
        return holder;
    }
    
    public void setHolder(String holder) {
        this.holder = holder;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public int getVersion() {
        return version;
    }
    
    public void setVersion(int version) {
        this.version = version;
    }
}
