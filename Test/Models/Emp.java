package Test.Models;
import ETU2058.framework.Annotation;

public class Emp {

    @Annotation(url="findAll")
    public void findAll(){
        System.out.println("findAll");
    }    

    public ModelView getModel(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");
        int value = 25;
        temp.addItem("Mety",value);

        return temp;
    }

}