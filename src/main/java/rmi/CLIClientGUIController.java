package rmi;

import aud.io.IGUIController;
import aud.io.rmi.ClientManager;

public class CLIClientGUIController implements IGUIController {
    private ClientManager manager;

    public CLIClientGUIController(ClientManager manager) {
        this.manager = manager;
    }

    @Override
    public void update() {
        if (manager.getParty() != null){
            System.out.println(manager.getPartyInfo());
        }
    }
}
