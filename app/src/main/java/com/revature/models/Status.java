package com.revature.models;

public enum Status {
    APPROVED,
    DENIED,
    PENDING;

    public static int toInt(Status status) {
        switch(status) {
            case APPROVED:
                return 1;
            case DENIED:
                return 2;
            case PENDING:
                return 3;
            default:
                return 0;
        }
    }

    public static Status toStatus(int i) {
        switch(i) {
            case 1:
                return APPROVED;
            case 2:
                return DENIED;
            case 3:
                return PENDING;
            default:
                return null;
        }
    }

}
