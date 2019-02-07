package db;

public interface DAO {

    public void insert(Object obj);

    public void update(int id);

    public void delete(int id);

    public Object read();

    public int size();

}
