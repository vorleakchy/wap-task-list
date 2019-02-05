package model;

public class Team {

    private int id;
    private String name;

    public Team() {
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj==null){

            return  false;
        }
        else{

          return this.id==((Team)obj).getId()?true:false;
        }

    }
}
