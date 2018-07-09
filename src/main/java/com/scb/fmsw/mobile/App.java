package com.scb.fmsw.mobile;

import sun.jvm.hotspot.utilities.Assert;

import java.io.File;
import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
        String prevActorValue = "1234567890";
        if (prevActorValue != null) {
            System.out.println("test");
            throw new RuntimeException("heelllooooo");
        }
    }
}
