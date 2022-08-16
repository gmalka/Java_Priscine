package classes;

import interfaces.PreProcessor;
import interfaces.Printer;
import interfaces.Renderer;

public class RendererStandardImpl implements Renderer {
    private PreProcessor preprocessor;

    public RendererStandardImpl(PreProcessor preprocessor)
    {
        this.preprocessor = preprocessor;
    }

    @Override
    public void rende(String str) {
        System.out.println(preprocessor.process(str));
    }
}
