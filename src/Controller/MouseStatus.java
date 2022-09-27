package Controller;

import Model.MyPoint;

public class MouseStatus {

    private MyPoint mouseLocation = new MyPoint(800.0 , 900.0);
    private boolean rightClick;
    private boolean leftClick;
    private boolean paused;

    public MyPoint getMouseLocation() {
        return mouseLocation;
    }

    public void setMouseLocation(MyPoint mouseLocation) {
        this.mouseLocation.x = mouseLocation.x;
        this.mouseLocation.y = mouseLocation.y;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public boolean isLeftClick() {
        return leftClick;
    }

    public void setLeftClick(boolean leftClick) {
        this.leftClick = leftClick;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public boolean isPaused() {

        return paused;
    }

    public void setMouseStatus(MouseStatus status) {

        this.setMouseLocation(status.mouseLocation);
        this.leftClick = status.leftClick;
        this.rightClick = status.rightClick;
        this.paused = status.paused;

    }
}
