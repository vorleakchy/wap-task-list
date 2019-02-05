package model;

public class User {
    private int id;
    private String name;
    private int idTeam;

    public User(){
    }

    public User(int id,String name,int idteam){
        this.id=id;
        this.name=name;
        this.idTeam=idteam;
    }

    public int getId() {
        return id;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }

    public void setName(String name) {
        this.name = name;
    }
}
