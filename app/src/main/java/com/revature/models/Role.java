package com.revature.models;

public enum Role {
    EMPLOYEE,
    MANAGER;

    public static int toInt(Role role) {
        switch (role) {
            case MANAGER:
                return 1;
            case EMPLOYEE:
                return 2;
            default:
                return 0;
        }
    }

    public static Role toRole(int i) {
        switch (i) {
            case 1:
                return MANAGER;
            case 2:
                return EMPLOYEE;
            default:
                return null;
        }
    }

}
