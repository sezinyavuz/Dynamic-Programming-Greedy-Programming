import java.time.LocalTime;

public class Task implements Comparable {
    public String name;
    public String start;
    public int duration;
    public int importance;
    public boolean urgent;

    /*
        Getter methods
     */
    public String getName() {
        return this.name;
    }

    public String getStartTime() {
        return this.start;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getImportance() {
        return this.importance;
    }

    public boolean isUrgent() {
        return this.urgent;
    }

    /**
     * Finish time should be calculated here
     *
     * @return calculated finish time as String
     */
    public String getFinishTime() {

        // YOUR CODE HERE

        LocalTime startTime = LocalTime.parse(this.start);
        LocalTime endTime = startTime.plusHours(this.duration);
        return String.format("%02d:%02d", endTime.getHour(), endTime.getMinute());
    }

    /**
     * Weight calculation should be performed here
     *
     * @return calculated weight
     */
    public double getWeight() {

        // YOUR CODE HERE

        double weight =(this.importance * (this.urgent ? 2000 : 1)) / this.duration;
        return weight;
    }

    /**
     * This method is needed to use {@link java.util.Arrays#sort(Object[])} ()}, which sorts the given array easily
     *
     * @param o Object to compare to
     * @return If self > object, return > 0 (e.g. 1)
     * If self == object, return 0
     * If self < object, return < 0 (e.g. -1)
     */
    @Override
    public int compareTo(Object o) {
        // YOUR CODE HERE
        Task other = (Task) o;
        return this.getFinishTime().compareTo(other.getFinishTime());
    }
}

