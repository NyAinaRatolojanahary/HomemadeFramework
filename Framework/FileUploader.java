package ETU2058.Framework;

public class FileUploader {
    
    String nom;
    byte[] bytes;
    String path;

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getNom() {
        return nom;
    }
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }
    public byte[] getBytes() {
        return bytes;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public String getPath() {
        return path;
    }

}
