package org.lynxz.third_generation.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import org.lynxz.third_generation.annotation.ThirdActivityAutoGenerator;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * 第三方库Activity自动生成
 */
public class ThirdActivityAutoGenerationProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Messager messager;
    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ThirdActivityAutoGenerator.class.getCanonicalName());
    }

    private String getPackageName(Element element) {
        if (element == null) {
            return "";
        }
        return elementUtils.getPackageOf(element).getQualifiedName().toString();
    }

    private Name getClassName(Element element) {
        if (element == null) {
            return null;
        }

        ElementKind kind = element.getKind();
        if (kind == ElementKind.CLASS) {
            TypeElement type = (TypeElement) element;
            return type.getSimpleName();
        } else {
            return getClassName(element.getEnclosingElement());
        }
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
//        return SourceVersion.RELEASE_7;
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(ThirdActivityAutoGenerator.class);
        for (Element element : elements) { // class 类型
            ThirdActivityAutoGenerator annotation = element.getAnnotation(ThirdActivityAutoGenerator.class);
            String applicationId = annotation.getApplicationId(); // app引用包名
            String subPackageName = annotation.getSubPackageName(); // 要创建的类所在子包名,可空, 如: wxapi
            String targetActivityName = annotation.getTargetActivityName(); // 类名, 如: WXEntryActivity

            // 获取父类信息
            Class<?> supperClass = null;
            try {
                supperClass = annotation.getSupperClass();
            } catch (MirroredTypeException mte) {
                DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
                TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
                String qualifiedSuperClassName = classTypeElement.getQualifiedName().toString(); // 完整类名
                try {
                    supperClass = Class.forName(qualifiedSuperClassName);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }

            // 生成类信息
            TypeSpec.Builder builder = TypeSpec.classBuilder(targetActivityName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
            if (supperClass != null) {
                builder.superclass(supperClass);
            }
            TypeSpec typeSpec = builder.build();

            // 写入到指定的java文件中
            // 获取完成包路径
            String packagePath = applicationId + "." + subPackageName;
            packagePath = packagePath.trim().replace("..", ".");
            if (packagePath.endsWith(".")) {
                packagePath = packagePath.substring(0, packagePath.length() - 1);
            }

            JavaFile helperJavaFile = JavaFile.builder(packagePath, typeSpec).build();
            try {
                helperJavaFile.writeTo(filer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
