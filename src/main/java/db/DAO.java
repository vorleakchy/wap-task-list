package db;

import java.io.IOException;

public interface DAO {

    public void insert(Object obj);

    public void update();

    public void delete(int id);

    public Object read();

    public int size();

}
