import java.util.List;

/**
 * Created by Huiyu on 2015/10/12.
 */
public class Test {
    public static void main(String[] args) {
        Elevator elevator = new Elevator();
        elevator.loadPeople(new People(1, 2));
        elevator.loadPeople(new People(4, 1));
        elevator.loadPeople(new People(1, 9));
        elevator.loadPeople(new People(8, 1));
        List elevatorList = elevator.getElevatorList();
        elevator.unloadPeople(2);
        System.out.println(elevatorList.size());
    }
}
