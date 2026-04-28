package Entity;

import Enum.PositionName;

// Entity.Position.java
public class Position {
    private int positionID;
    private PositionName positionName;

    public Position() {
    }

    public Position(int positionID, PositionName positionName) {
        this.positionID = positionID;
        this.positionName = positionName;
    }

    public int getPositionID() {
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public PositionName getPositionName() {
        return positionName;
    }

    public void setPositionName(PositionName positionName) {
        this.positionName = positionName;
    }
}