package QueryHandler;


import javax.print.attribute.standard.Media;
import java.util.List;

/**
 * Created by jls on 2/16/15.
 */
public interface Query {
    void createTable();

    void insert();

    Results selectById(int id);

    List<Results> selectAll();

    void delete();

    void update(Media media, int id);
}
