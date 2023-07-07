package Test.Models;
import java.sql.Date;

import ETU2058.Framework.Annotation;
import ETU2058.Framework.ModelView;
import ETU2058.Framework.FileUploader;
import ETU2058.Framework.Parametre;
import ETU2058.Framework.Scope;

@Scope(scope="Emp")
public class Emp {

    String nom;
    Date sqlDate;
    java.util.Date utilDate; 
    String[] table;
    FileUploader file;

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public void setSqlDate(Date sqlDate) {
        this.sqlDate = sqlDate;
    }
    public Date getSqlDate() {
        return sqlDate;
    }
    public void setUtilDate(java.util.Date utilDate) {
        this.utilDate = utilDate;
    }
    public java.util.Date getUtilDate() {
        return utilDate;
    }
    public void setTable(String[] table) {
        this.table = table;
    }
    public String[] getTable() {
        return table;
    }

    public void setFile(FileUploader file) {
        this.file = file;
    }
    public FileUploader getFile() {
        return file;
    }



    @Annotation(url="findAll")
    public void findAll(){
        System.out.println("findAll");
    }   

    @Annotation(url="getUrl")
    public ModelView getModel(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");
        int value = 25;
        temp.addItem("Mety",value);

        return temp;
    } 

    @Annotation(url="getVal")
    public ModelView getValueFromView(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");
        // for (int i = 0; getTable() != null && i < getTable().length; i++) {
        //     System.out.println(getTable()[i]);
        // }
        return temp;
    }

    @Annotation(url="getFile")
    public ModelView getFiles(){
        ModelView temp = new ModelView();
        temp.addItem("img",this.getFile().getNom());
        temp.setView("Teste.jsp");

    @Annotation(url="getParam")
    public ModelView getParam(@Parametre(param="param")Integer i){
        ModelView temp = new ModelView();
        temp.addItem("test",i);
        temp.setView("Teste.jsp");
        return temp;
    }

}