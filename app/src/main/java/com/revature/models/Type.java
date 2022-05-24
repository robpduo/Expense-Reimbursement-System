package com.revature.models;

public enum Type {
    BLANK,
    LODGING,
    TRAVEL,
    FOOD,
    OTHER;

    public static int toInt(Type type) {
        switch(type) {
            case LODGING:
                return 1;
            case TRAVEL:
                return 2;
            case FOOD:
                return 3;
            case OTHER:
                return 4;
            default:
                return 0;
        }
    }

    public static Type toType(int i) {
        switch(i) {
            case 1:
                return LODGING;
            case 2:
                return TRAVEL;
            case 3:
                return FOOD;
            case 4:
                return OTHER;
            default:
                return BLANK;
        }
    }

}
