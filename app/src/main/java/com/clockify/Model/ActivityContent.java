package com.clockify.Model;

import java.lang.reflect.Type;

public interface ActivityContent {
    public static final int Type_Date=0;
    public static final int Type_Content=1;

    abstract public int getType();
}
