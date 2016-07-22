package com.example.wheather.view.model;

/**
 * Created by Aleksandr on 21.07.2016 in Weather.
 */
public class ConvertUnits {
    private static final double pressureAspect = 1.3332239d;
    public static int hPa_to_mm(double hPaValue){
        return (int)(hPaValue/pressureAspect);
    }
}
