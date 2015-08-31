package QueryHandler;

/**
 * Created by jls on 2/16/15.
 */
public class Results {

    private int id;
    private int totalVisits;
    private int totalVisitors;
    private int averageVisitsPerson;
    private int votes;
    private int appreciation;

    public Results() {
    }

    public Results(int id, int totalVisits, int totalVisitors, int averageVisitsPerson, int votes, int appreciation) {
        this.id = id;
        this.totalVisits = totalVisits;
        this.totalVisitors = totalVisitors;
        this.averageVisitsPerson = averageVisitsPerson;
        this.votes = votes;
        this.appreciation = appreciation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }

    public int getTotalVisitors() {
        return totalVisitors;
    }

    public void setTotalVisitors(int totalVisitors) {
        this.totalVisitors = totalVisitors;
    }

    public int getAverageVisitsPerson() {
        return averageVisitsPerson;
    }

    public void setAverageVisitsPerson(int averageVisitsPerson) {
        this.averageVisitsPerson = averageVisitsPerson;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getAppreciation() {
        return appreciation;
    }

    public void setAppreciation(int appreciation) {
        this.appreciation = appreciation;
    }
}
