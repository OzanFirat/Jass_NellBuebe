package Client.Model;

public class PlayerScoreTuple<String, Int> implements Comparable<PlayerScoreTuple>{
    private Int points;
    private final String name;

    public PlayerScoreTuple(String name, Int points) {
        this.points = points;
        this.name = name;
    }

    public Int getPoints() {
        return points;
    }

    public String getName() {
        return name;
    }

    public void setPoints(Int points) {
        this.points = points;
    }

    public PlayerScoreTuple comparePoints(PlayerScoreTuple tuple) {
        int yourPoints = (Integer) this.getPoints();
        int oppPoints = (Integer) tuple.getPoints();
        if (yourPoints > oppPoints) {
            return this;
        } else {
            return tuple;
        }
    }

    @Override
    public int compareTo(PlayerScoreTuple o) {
        int yourPoints = (Integer) this.points;
        int otherPoints = (Integer) o.getPoints();
        int order =  otherPoints - yourPoints;
        return order;
    }
}
