package model;

public enum RoomType {
    UNKNOWN,
    SINGLE,
    DOUBLE;

    public static RoomType StringToEnum(String roomType) {
        if(roomType.equals("Single")|| roomType.equals("SINGLE")) {
            return SINGLE;
        }
        if(roomType.equals("Double")|| roomType.equals("DOUBLE")) {
            return DOUBLE;
        }
        return UNKNOWN;
    }
    @Override
    public String toString() throws IllegalArgumentException{
        if(this == SINGLE) {return "Single Room";}
        else if(this == DOUBLE) {return "Double Room";}
        else {return "Unknown room type";}
    }
}


