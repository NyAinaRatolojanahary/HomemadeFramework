package ETU2058.Framework;

public class ModelView{

    String view;
    HashMap data;

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

    public void addItem(String cle, Object valeur){
        this.getData().put(cle,valeur);
    }

}