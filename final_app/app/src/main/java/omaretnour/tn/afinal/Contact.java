package omaretnour.tn.afinal;

public class Contact {
    private String user ;
    private String nom ;
    private String num ;

    public Contact(String user, String nom, String num) {
        this.user = user;
        this.nom = nom;
        this.num = num;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
