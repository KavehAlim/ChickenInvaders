package MultiPlayer.Client;

import Controller.MouseStatus;
import Reflection.ReflectionExtension;

public class ClientStatus {

    public boolean observer;
    public MouseStatus mouseStatus;
    public int id;
    public ReflectionExtension newClass;

    public ClientStatus(boolean observer){

        this.observer = observer;
        mouseStatus = new MouseStatus();
        newClass = new ReflectionExtension();

    }

    public void setClientStatus(ClientStatus status){

        this.id = status.id;
        mouseStatus.setMouseStatus(status.mouseStatus);

    }
}
