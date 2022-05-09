package com.revature.models;

public enum Role {
    EMPLOYEE,
    MANAGER;

    public static int toInt(Role role) {
        switch (role) {
            case EMPLOYEE:
                return 1;
            case MANAGER:
                return 2;
            default:
                return 0;
        }
    }

    public static Role toRole(int i) {
        switch (i) {
            case 1:
                return EMPLOYEE;
            case 2:
                return MANAGER;
            default:
                return null;
        }
    }

}
