package Test.Models;
import ETU2058.Framework.Annotation;
import ETU2058.Framework.ModelView;

public class Emp {

    @Annotation(url="findAll")
    public void findAll(){
        System.out.println("findAll");
    }   

    @Annotation(url="getUrl")
    public ModelView getModel(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");

        return temp;
    } 

    public ModelView getModel(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");
        int value = 25;
        temp.addItem("Mety",value);

        return temp;
    }

}