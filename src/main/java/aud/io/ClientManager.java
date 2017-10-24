package aud.io;

import aud.io.fontyspublisher.IRemotePropertyListener;
import aud.io.fontyspublisher.IRemotePublisherForListener;

import java.beans.PropertyChangeEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientManager  extends UnicastRemoteObject implements IRemotePropertyListener {

    private IRemotePublisherForListener publisher;
    private IPartyManager server;

    public ClientManager(IRemotePublisherForListener publisher, IPartyManager server) throws RemoteException {
        this.publisher = publisher;
        this.server = server;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) throws RemoteException {

    }
}
