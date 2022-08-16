package classes;

import interfaces.PreProcessor;
import interfaces.Renderer;

public class RendererErrImpl implements Renderer {
    private PreProcessor preprocessor;

    public RendererErrImpl(PreProcessor preprocessor)
    {
        this.preprocessor = preprocessor;
    }

    @Override
    public void rende(String str) {
        System.err.println(preprocessor.process(str));
    }
}
