package com.godspeed.compiler;

import com.godspeed.annotation.GenerateJSONModelCovert;
import com.godspeed.annotation.GenerateTableDao;
import com.godspeed.compiler.handler.JSONModelHandler;
import com.godspeed.compiler.handler.TableHandler;
import com.godspeed.compiler.model.ProcessorManager;
import com.google.auto.service.AutoService;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class GodspeedProcesser extends AbstractProcessor {

    private ProcessorManager manager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);


        manager = new ProcessorManager(processingEnv);
        manager.addHandlers(new JSONModelHandler(processingEnv));
        manager.addHandlers(new TableHandler(processingEnv));

    }

    /**
     * @return 指定哪些注解应该被注解处理器注册
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(GenerateJSONModelCovert.class.getCanonicalName());
        types.add(GenerateTableDao.class.getCanonicalName());
        return types;
    }

    /**
     * @return 指定使用的 Java 版本。通常返回 SourceVersion.latestSupported()。
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {


        manager.handle(manager, roundEnv);



        return true;
    }
}