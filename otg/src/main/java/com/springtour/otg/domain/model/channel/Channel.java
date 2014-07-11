package com.springtour.otg.domain.model.channel;

import com.springtour.otg.application.exception.UnavailableCurrencyException;

import org.apache.commons.lang.builder.StandardToStringStyle;

import com.springtour.otg.domain.shared.WrapCurrency;
import com.springtour.shared.Entity;
import com.springtour.util.ObjectUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 支付渠道
 * 
 * @author Hippoom
 * 
 */
public class Channel implements Entity<Channel> {

    private static final String AVAILABLE_CURRENCIES_SPERATOR = ",";
    private String name;
    private List<Gateway> gateways;
    private String availableCurrencies;
    private boolean availableCurrenciesUpdated = false;
    private boolean gatewaysUpdated = false;
    private boolean persistent = true;

    public Channel(String id) {
        this.id = id;
        this.persistent = false;
    }

    public void updateAvailableCurrencies(String availableCurrencies) {
        this.setAvailableCurrencies(availableCurrencies);
        this.availableCurrenciesUpdated = true;
    }

    public List<String> availableCurrencyCodes() throws UnavailableCurrencyException {
        List<String> result = new ArrayList<String>();
        for (WrapCurrency currency : availableCurrencies()) {
            result.add(currency.getCode());
        }
        return result;
    }
    
    public List<String> gatewayCodes() throws UnavailableCurrencyException {
        List<String> result = new ArrayList<String>();
        for (Gateway gateway : getGateways()) {
            result.add(gateway.getCode());
        }
        return result;
    }

    public List<WrapCurrency> availableCurrencies() throws UnavailableCurrencyException {
        if (this.availableCurrencies == null) {
            return Collections.EMPTY_LIST;
        } else {
            List<WrapCurrency> result = new ArrayList<WrapCurrency>();
            final String[] currencyCodes = this.availableCurrencies.split(AVAILABLE_CURRENCIES_SPERATOR);
            for (String currencyCode : currencyCodes) {
                result.add(WrapCurrency.valueOf(currencyCode.trim()));
            }
            return result;
        }
    }

    public void updateGateways(List<Gateway> gateways) {
        this.setGateways(gateways);
        this.gatewaysUpdated = true;
    }

    public Gateway getGateway(String code) {
        for (Gateway gateway : ObjectUtils.nullSafe(this.gateways, new ArrayList<Gateway>())) {
            if (gateway.sameCodeAs(code)) {
                return gateway;
            }
        }
        return null;
    }

    public boolean support(String gatewayCode) {
        for (Gateway gateway : ObjectUtils.nullSafe(this.gateways, new ArrayList<Gateway>())) {
            if (gateway.sameCodeAs(gatewayCode)) {
                return true;
            }
        }
        return false;
    }

    public boolean support(WrapCurrency currency) {
        return ObjectUtils.nullSafe(this.availableCurrencies, "").contains(currency.getCode());
    }

    @Override
    public boolean sameIdentityAs(final Channel other) {
        return (other != null) && (this.id.equals(other.getId()));
    }

    public boolean sameIdentityAs(String other) {
        return (other != null) && (this.id.equals(other));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, StandardToStringStyle.SHORT_PREFIX_STYLE).append(id).toString();
    }

    public boolean isPersistent() {
        return persistent;
    }
    private String id;
    private int version = 1;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Gateway> getGateways() {
        return gateways;
    }

    public void setGateways(List<Gateway> gateways) {
        this.gateways = gateways;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    /**
     * needed by orm
     */
    public Channel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(String availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public boolean isUpdated() {
        return isGatewaysUpdated() || isAvailableCurrenciesUpdated();
    }

    public boolean isGatewaysUpdated() {
        return gatewaysUpdated;
    }

    public void resetUpdated() {
        gatewaysUpdated = false;
        availableCurrenciesUpdated = false;
    }

    public boolean isAvailableCurrenciesUpdated() {
        return availableCurrenciesUpdated;
    }

    public void resetPersistent() {
        this.persistent = true;
    }
}
