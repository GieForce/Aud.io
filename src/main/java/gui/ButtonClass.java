package gui;

import javafx.scene.control.Button;

public class ButtonClass extends Button {
    private Object obj;

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public ButtonClass(Object obj) {
        this.obj = obj;
    }
}
