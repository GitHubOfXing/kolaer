package cn.tech.routerlib;

import com.google.auto.service.AutoService;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//@AutoService(Processor.class) 这个有木有？这是一个注解处理器，是Google开发的，
//用来生成META-INF/services/javax.annotation.processing.Processor文件的。
//引入方式     compile 'com.google.auto.service:auto-service:1.0-rc2'
@SuppressWarnings("unchecked")
@AutoService(Processor.class)
public class MyProcessor extends AbstractProcessor {

    private Types mTypeUtils;
    private Elements mElementUtils;
    private Filer mFiler;
    private Messager mMessager;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        //把我们自己定义的注解添加进去
        annotations.add("com.uxin.middleware.internal.MethodProcessor");
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        mTypeUtils = processingEnv.getTypeUtils();
        mElementUtils = processingEnv.getElementUtils();
        mFiler = processingEnv.getFiler();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
    	// // 遍历所有被注解了的元素

        System.out.println("------");
        info(">>> Found field, start... <<<");

        Class clz = null;
        try {
            clz = Class.forName("com.uxin.middleware.internal.MethodProcessor");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Set<Element> sets = roundEnv.getElementsAnnotatedWith(clz);

        for (Element annotatedElement : sets) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s", "com.uxin.middleware.internal.MethodProcessor");
                return true;
            }
            // //解析，并生成代码
            try {
                analysisAnnotated(annotatedElement);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private void error(Element e, String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private static final String SUFFIX = "Test";
    private static final String packageName = "com.company.processtest";
    private static final String retStr = "";

    private void analysisAnnotated(Element classElement) throws ClassNotFoundException {
//        Class clz = Class.forName("com.uxin.middleware.internal.MethodProcessor");
//        MethodProcessor annotation = classElement.getAnnotation(clz);
        String name = "MethodProcessor";

        String newClassName = name + SUFFIX;

        StringBuilder builder = new StringBuilder()
                .append("package " + packageName + ";\n\n")
                .append("public class ")
                .append(newClassName)
                .append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append(retStr).append(" !\\n");

        builder.append("\";\n") // end return
                .append("\t}\n") // close method
                .append("}\n"); // close class

        try { // write the file
            JavaFileObject source = mFiler.createSourceFile(packageName + "." + newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
        }
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(
                Diagnostic.Kind.NOTE,
                String.format(msg, args));
    }
}

