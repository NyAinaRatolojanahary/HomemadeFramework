package ETU2058.Framework;
import java.util.HashMap;

public class ModelView{

    String view;
    HashMap<String, Object> data = new HashMap<String,Object>();
    HashMap<String, Object> session = new HashMap<String, Object>();
  
    public String getView(){
        return this.view;
    }

    public void setView(String vue){
        this.view = vue;
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }

    public void setData(HashMap<String, Object> data){
        this.data = data;
    }

    public void setSession(HashMap<String, Object> session) {
        this.session = session;
    }
    public HashMap<String, Object> getSession() {
        return session;
    }

    public void addItem(String cle, Object valeur){
        this.getData().put(cle,valeur);
    }

    public void addSession(String key, Object object) {
        this.getSession().put(key, object);
    }

}