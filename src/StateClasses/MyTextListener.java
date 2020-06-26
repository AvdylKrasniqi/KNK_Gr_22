package StateClasses;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class MyTextListener implements PropertyChangeListener {

    public MyTextListener() {

    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        if (event.getPropertyName().equals("MyTextProperty")) {
            System.out.println(event.getNewValue().toString());
        // updateStatus() ;
        }
    }
}