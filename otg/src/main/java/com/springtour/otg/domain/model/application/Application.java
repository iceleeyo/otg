package com.springtour.otg.domain.model.application;

import lombok.Getter;

public enum Application {
    AIR("9"), TICKET("10"), CRUISE("11"), VISA("12"), GROUPTOUR("13"), FREETOUR("14");
    
    @Getter
    private final String id;
    
    private Application(String id) {
        this.id = id;
    }
}
