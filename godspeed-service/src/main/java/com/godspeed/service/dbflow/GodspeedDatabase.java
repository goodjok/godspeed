package com.godspeed.service.dbflow;


public abstract class GodspeedDatabase {
    public static Class<GodspeedDatabase> databaseClass;

    public GodspeedDatabase(Class<GodspeedDatabase> clazz){
        databaseClass = clazz;
    }

}