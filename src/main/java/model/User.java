package model;

public class User {
    private int id;
    private String name;
    private Team team;

    public User(){
    }

    public User(int id,String name,Team team){
        this.id=id;
        this.name=name;
        this.team=team;
    }

    public int getId() {
        return id;
    }

    public Team getIdTeam() {
        return team;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeam(Team obteam) {
        this.team = obteam;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==null){

            return  false;
        }
        else{

            return this.id==((User)obj).getId()?true:false;
        }

    }
}
