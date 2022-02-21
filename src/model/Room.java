package model;

public class Room implements IRoom{
    protected String roomNumber;
    protected Double price;
    protected RoomType roomType;

    public Room(String roomNumber, Double price, RoomType roomType) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.roomType = roomType;
    }

    @Override
    public String getRoomNumber() {
        return this.roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public boolean isFree() {
        if(this.price == 0.0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean equals(Object other) {
        if(other == this)
            return true;
        if(!(other instanceof Room))
            return false;
        Room o = (Room)other;
        return o.roomNumber.equals(roomNumber) && o.price == price && o.roomType == roomType;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + roomNumber.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + roomType.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Room " + this.roomNumber + ": " + this.roomType + ", $" + this.price + " per night";
    }
}
