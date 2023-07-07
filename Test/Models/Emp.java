package Test.Models;
import java.sql.Date;
import java.util.HashMap;

import ETU2058.Framework.Annotation;
import ETU2058.Framework.ModelView;
import ETU2058.Framework.FileUploader;
import ETU2058.Framework.Parametre;
import ETU2058.Framework.Scope;
import ETU2058.Framework.Authentification;
import ETU2058.Framework.Session;

@Scope(scope="Emp")
public class Emp {

    String nom;
    Date sqlDate;
    java.util.Date utilDate; 
    String[] table;
    FileUploader file;
    HashMap<String, Object> session;

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
    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    public HashMap<String, Object> getSession() {
        return session;
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
    @Authentification(profil="admin")
    @Annotation(url="getVal")
    public ModelView getValueFromView(){
        ModelView temp = new ModelView();
        temp.setView("Teste.jsp");
        // System.out.println(this.getNom());
        // System.out.println(this.getSqlDate());
        // System.out.println(this.getUtilDate());
        for (int i = 0; getTable() != null && i < getTable().length; i++) {
            System.out.println(getTable()[i]);
        }
        
        return temp;
    }
    @Annotation(url="getParam")
    public ModelView getParam(@Parametre(param="param")Integer i){
        ModelView temp = new ModelView();
        temp.addItem("test",i);
        temp.setView("Teste.jsp");
        return temp;
    }
    @Annotation(url="getFile")
    public ModelView getFiles(){
        ModelView temp = new ModelView();
        temp.addItem("img",this.getFile().getNom());
        temp.setView("Teste.jsp");
        
        return temp;
    }
    @Annotation(url="login")
    public ModelView login() {
        ModelView temp = new ModelView();
        temp.addSession("isConnected", true);
        temp.addSession("profil","admin");
        temp.setView("index.jsp");
        return temp;
    }

    @Session
    @Annotation(url="session")
    public ModelView testSession() {
        ModelView temp = new ModelView();
        this.getSession().put("profil","test");
        temp.addItem("session",this.getSession());
        temp.setView("index.jsp");
        return temp;
    }

}