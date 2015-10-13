/**
 * Created by Huiyu on 2015/10/12.
 */
public class People {
    private int loadFloor;
    private int unloadFloor;
    private int upOrDown;

    public People(int loadFloor, int unloadFloor) {
        this.loadFloor = loadFloor;
        this.unloadFloor = unloadFloor;
        if (unloadFloor > loadFloor) {
            this.upOrDown = 1;
        } else if (unloadFloor < loadFloor) {
            this.upOrDown = 0;
        }
    }

    public int getLoadFloor() {
        return loadFloor;
    }

    public int getUnloadFloor() {
        return unloadFloor;
    }

    public int getUpOrDown() {
        return upOrDown;
    }
}
