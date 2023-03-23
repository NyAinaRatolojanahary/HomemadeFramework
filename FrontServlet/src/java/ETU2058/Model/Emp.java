/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ETU2058.Model;

import java.time.LocalDate;
import java.time.Period;

/**
 *
 * @author ASUS
 */
public class Emp {
    
    public String nomEmp;
    public String numEmp;
    public String dateNaissance;

    public String getNomEmp() {
        return nomEmp;
    }

    public void setNomEmp(String nomEmp) {
        this.nomEmp = nomEmp;
    }

    public String getNumEmp() {
        return numEmp;
    }

    public void setNumEmp(String numEmp) {
        this.numEmp = numEmp;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    
    
    
    public int calculAge(String datenaissance){
        LocalDate dt = LocalDate.parse(datenaissance);
        Period period = Period.between(dt, LocalDate.now());
        int year = period.getYears();
        
        return year;
    }
}
