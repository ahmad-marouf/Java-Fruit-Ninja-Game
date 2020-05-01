package com.controllers;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class EnumAdapter extends XmlAdapter<String,ENUM> {
    @Override
    public ENUM unmarshal(String value) throws Exception {
        if (value.equalsIgnoreCase("APPLE"))
        return ENUM.APPLE;
        else if (value.equalsIgnoreCase("ORANGE"))
            return ENUM.ORANGE;
        else if (value.equalsIgnoreCase("STRAWBERRY"))
            return ENUM.STRAWBERRY;
        else if (value.equalsIgnoreCase("WATERMELON"))
            return ENUM.WATERMELON;
        else if (value.equalsIgnoreCase("PINEAPPLE"))
            return ENUM.PINEAPPLE;
        else if (value.equalsIgnoreCase("FATAL"))
            return ENUM.FATAL;
        else if (value.equalsIgnoreCase("DANGEROUS"))
            return ENUM.DANGEROUS;
        else
            return ENUM.valueOf(value);
    }

    @Override
    public String marshal(ENUM v) throws Exception {
        return v.toString();
    }

}
