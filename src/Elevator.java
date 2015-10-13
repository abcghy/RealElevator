import java.util.ArrayList;
import java.util.List;

/**
 * Created by Huiyu on 2015/10/12.
 */
public class Elevator {
    private List elevatorList = new ArrayList<People>();
    private int currentFloor;
    private int direction;
    public Elevator() {
        currentFloor = 1;
        direction = 1;
    }

    public void loadPeople(People p) {
        elevatorList.add(p);
    }

    public void unloadPeople(People p) {
        elevatorList.remove(p);
    }

    public void unloadPeople(int i) {
        elevatorList.remove(i);
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }

    public List getElevatorList() {
        return elevatorList;
    }
}
