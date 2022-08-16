package classes;

import interfaces.Printer;
import interfaces.Renderer;

public class PrinterWithPrefixImpl implements Printer {
    private String prefix;
    private Renderer renderer;

    public PrinterWithPrefixImpl(Renderer renderer)
    {
        this.renderer = renderer;
    }

    public void setPrefix(String prefix)
    {
        this.prefix = prefix;
    }

    @Override
    public void print(String str) {
        renderer.rende(prefix + " " + str);
    }
}
