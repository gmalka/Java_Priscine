package classes;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@SupportedAnnotationTypes({"classes.HtmlForm", "classes.HtmlInput"})
@SupportedSourceVersion(value = SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class HtmlProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        StringBuilder builder = new StringBuilder();
        for(Element element : roundEnv.getElementsAnnotatedWith(HtmlForm.class))
        {
            HtmlForm annotation = element.getAnnotation(HtmlForm.class);

            builder.append("<form action = \"")
                    .append(annotation.action())
                    .append("\" method = \"")
                    .append(annotation.method())
                    .append("\">\n");

            List<? extends Element> list =  element.getEnclosedElements();

            for (Element el : roundEnv.getElementsAnnotatedWith(HtmlInput.class))
            {
                if (!list.contains(el))
                    continue;
                HtmlInput input = el.getAnnotation(HtmlInput.class);
                builder.append("\t<input type = \"")
                        .append(input.type())
                        .append("\" name = \"")
                        .append(input.name())
                        .append("\" placeholder = \"")
                        .append(input.placeholder())
                        .append("\">\n");
            }
            builder.append("\t<input type = \"submit\" value = \"Send\">\n</form>");
            try (BufferedWriter writer = new BufferedWriter(
                    new FileWriter("target/classes/" + annotation.fileName()))) {
                writer.write(builder.toString());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }
}