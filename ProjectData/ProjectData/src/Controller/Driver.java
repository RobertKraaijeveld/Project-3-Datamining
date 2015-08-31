package Controller;

import DisplayManager.Display;

import javax.swing.*;

/**
 * Created by jls on 2/10/15.
 */
public class Driver {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Display.getInstance();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (UnsupportedLookAndFeelException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
