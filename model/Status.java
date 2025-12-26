package model;

public enum Status {
    PENDING,PREPARING,READY,DELIVERED;


    public Status next(){
        switch (this) {
            case PENDING:
                return PREPARING;
            case PREPARING:
                return READY;
            case READY:
                return DELIVERED;
            default:
                return PENDING;

        }
    }
}
