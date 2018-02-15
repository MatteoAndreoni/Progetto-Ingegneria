package SupportClasses;

import javax.swing.*;

public abstract class Observer extends JFrame
{
    protected Subject subject;

    public abstract void update(Subject obj);
}
