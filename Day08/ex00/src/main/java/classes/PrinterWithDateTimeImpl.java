package classes;

import interfaces.Printer;
import interfaces.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {

    private LocalDateTime time;
    private Renderer render;

    public PrinterWithDateTimeImpl(Renderer render)
    {
        this.render = render;
    }

    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }

    @Override
    public void print(String str) {
        render.rende(str + time);
    }
}
