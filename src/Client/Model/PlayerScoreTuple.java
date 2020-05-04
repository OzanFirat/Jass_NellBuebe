package Client.Model;

public class PlayerScoreTuple<String, Int> {
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
}
