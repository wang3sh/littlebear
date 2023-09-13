package com.yupi.wsyoj.judge.codeSandbox.TypeEnum;

import java.lang.reflect.Type;
import java.util.Arrays;

public enum Types {

    EXAMPLE(1, "example"),
    REMOTE(2, "remote"),
    THRIDPARTY(3, "ThirdParty");


    Types(int typeNum, String type) {
        this.typeNum = typeNum;
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public int getTypeNum() {
        return this.typeNum;
    }

    private final int typeNum;
    private final String type;

    public static Types getType(int t) {
        for(Types type: Types.values()) {
            if(type.getTypeNum() == t) {
                return type;
            }
        }
        return null;
    }
}
