package com.controllers;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "objectType")
@XmlEnum(String.class)
@XmlAccessorType(XmlAccessType.FIELD)
public enum ENUM {
    @XmlEnumValue("APPLE")
    APPLE("APPLE"),
    @XmlEnumValue("ORANGE")
    ORANGE("ORANGE"),
    @XmlEnumValue("STRAWBERRY")
    STRAWBERRY("STRAWBERRY"),
    @XmlEnumValue("WATERMELON")
    WATERMELON("WATERMELON"),
    @XmlEnumValue("PINEAPPLE")
    PINEAPPLE("PINEAPPLE"),
    @XmlEnumValue("FATAL")
    FATAL("FATAL"),
    @XmlEnumValue("DANGEROUS")
    DANGEROUS("DANGEROUS");

    private String value;

    ENUM(String value){
        this.value = value;
    }

    public String Value() { return value;}

    public static ENUM fromValue(String v) {
        for (ENUM c: ENUM.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
