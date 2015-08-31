package DataVisuals;

/**
 * Created by jls on 3/23/15.
 */
public class DatasetObject {

    private final int value;
    private final String name;
    private final String category;
    private final String date;
    private final String time;

    public DatasetObject(int value, String name, String category, String date, String time) {
        this.value = value;
        this.name = name;
        this.category = category;
        this.date = date;
        this.time = time;
    }

    public int getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
