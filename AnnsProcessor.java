import javax.annotation.processing.*;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.Element;
import java.io.*;
import javax.lang.model.SourceVersion;
import java.io.FileWriter;
import java.io.IOException;
import javax.tools.JavaFileObject;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.lang.StringBuilder;
import java.util.HashSet;
import javax.lang.model.element.AnnotationMirror;

@SupportedAnnotationTypes({"Disjunct", "Transitions", "TransitionWrapper"})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class AnnsProcessor extends AbstractProcessor {
    private File f;
    private Messager messager;
    private FileWriter w;
    @Override public synchronized void init(ProcessingEnvironment processingEnv){
        super.init(processingEnv);
        this.messager = processingEnv.getMessager();
        try{
            f = new File("facts.pl");
            if(f.delete()){
                f.createNewFile();
            }
            w = new FileWriter("facts.pl", true);
            w.append(":- use_module(library(mcintyre)).\n");
            w.append(":- mc.\n");
            w.append(":- begin_lpad.\n");
        }catch(IOException e){
            e.printStackTrace();
        }


    }

    @Override
    public boolean process(final Set< ? extends TypeElement > annotations, final RoundEnvironment roundEnv) {
        annotations.forEach(annotation -> roundEnv.getElementsAnnotatedWith(annotation).forEach(this::help));
        try{
            w.append(":- end_lpad.\n");
            w.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
        return true;
    }

    private void help(Element elem){
        try{
            for(AnnotationMirror am : elem.getAnnotationMirrors()){
                //System.out.println(am.getAnnotationType().toString());
                if(am.getAnnotationType().toString().equals("TransitionWrapper")){
                    TransitionWrapper tw = (TransitionWrapper)elem.getAnnotation(TransitionWrapper.class);
                    for(Transition t : tw.value()){ //builds one line of our cplint base
                        StringBuilder s = new StringBuilder();
                        String currentState = t.curr();
                        String[] newStates = t.newState();
                        double[] probs = t.probs();
                        for(int i = 0; i < t.newState().length; i++){
                            s.append("trans(" + currentState + ", S, "+ newStates[i] + "): " + probs[i]);

                            if (i < newStates.length-1){
                                s.append("; ");
                            }else{
                                s.append(".\n");
                            }
                            
                        }
                        w.append(s.toString());
                    }
                }
                if(am.getAnnotationType().toString().equals("DisjunctWrapper")){
                    DisjunctWrapper dw = (DisjunctWrapper)elem.getAnnotation(DisjunctWrapper.class);
                    StringBuilder s = new StringBuilder();
                    if(dw.head() != -1 && dw.tail().length != 0){
                        Disjunct d = dw.value()[dw.head()];
                        s.append(d.fact() + "(");
                        for(int i = 0; i < d.args().length; i++){
                            s.append(d.args()[i]);
                            if(i < d.args().length - 1){
                                s.append(",");
                            }
                        }
                        s.append("):- ");
                        for (int q = 0; q < dw.tail().length; q++){
                            Disjunct d2 = dw.value()[dw.tail()[q]];
                            s.append(d2.fact() + "(");
                            for(int i = 0; i < d2.args().length; i++){
                                s.append(d2.args()[i]);
                                if(i < d2.args().length - 1){
                                    s.append(",");
                                }
                            }
                            s.append(")");
                            if(q < dw.tail().length-1 ){
                                s.append(", ");
                            }
                            else{
                                s.append(".\n");
                            }
                        }
                    }
                    int j;
                    if(dw.head() != -1){
                        j = 1 + dw.tail().length;
                    }
                    else{
                        j = dw.tail().length;
                    }
                    for(; j < dw.value().length; j++){
                        Disjunct d = dw.value()[j];
                        s.append(d.fact() + "(");
                        for(int i = 0; i < d.args().length; i++){
                            s.append(d.args()[i]);
                            if(i < d.args().length - 1){
                                s.append(",");
                            }
                        }
                        s.append(").\n");
                    }
                    w.append(s.toString());
                }
            } 
        }
        catch (IOException io){
            io.printStackTrace();
        }


    }
        
}