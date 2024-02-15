import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        // YOUR CODE HERE

        int low = 0;
        int high = index - 1;
        int mid;
        while (low <= high) {
            mid = (low + high) / 2;

            int minutes1 = Integer.parseInt(this.taskArray[mid].getFinishTime().substring(0, 2)) * 60 + Integer.parseInt(this.taskArray[mid].getFinishTime().substring(3));
            int minutes2 = Integer.parseInt(this.taskArray[index].getStartTime().substring(0, 2)) * 60 + Integer.parseInt(this.taskArray[index].getStartTime().substring(3));
            int minutes3 = Integer.parseInt(this.taskArray[mid+1].getFinishTime().substring(0, 2)) * 60 + Integer.parseInt(this.taskArray[mid+1].getFinishTime().substring(3));
            if (minutes1 <= minutes2) {
                if (minutes3 <= minutes2) {
                    low = mid + 1;
                } else {
                    return mid;
                }
            } else {
                high = mid - 1;
            }
        }
        return -1;

    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        // YOUR CODE HERE
        for( int i = 0 ; i < this.taskArray.length; i++){
            this.compatibility[i] = binarySearch(i);
        }

    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        // YOUR CODE HERE

        int i = taskArray.length - 1;
        calculateCompatibility();
        System.out.println("Calculating max array\n" +
                "---------------------");
        calculateMaxWeight(i);
        System.out.println();


        System.out.println("Calculating the dynamic solution\n" +
                "--------------------------------");
        solveDynamic(i);
        System.out.println("\nDynamic Schedule\n" +
                "----------------");

        Collections.reverse(planDynamic);
        for(int j =0 ; j< planDynamic.size();j++){
            System.out.println("At " + planDynamic.get(j).getStartTime()+", "+planDynamic.get(j).getName()+".");
        }

        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        // YOUR CODE HERE


        if (i < 0) {
            return;
        }

        System.out.println("Called solveDynamic(" + i + ")");

        // Check if it is better to include task i or not
        Double weight1 = taskArray[i].getWeight();
        int compatibleIndex = compatibility[i];
        if (compatibleIndex != -1) {
            weight1 += maxWeight[compatibleIndex];
        }
        Double weight2 = 0.0;

        if(i -1 > 0){
         weight2 = maxWeight[i-1];
        }

        if (weight1 >= weight2) {
            // Add task i to the planDynamic
            planDynamic.add(taskArray[i]);
            // Recursive call to solveDynamic with i - 2
            solveDynamic(compatibility[i]);
        } else {
            // Recursive call to solveDynamic with i - 1
            solveDynamic(i - 1);
        }

    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        // YOUR CODE HERE
        if (i < 0) {

            System.out.println("Called calculateMaxWeight(" + i + ")");
            return 0.0;
        }


        if (i != 0 && maxWeight[i] != null) {
            System.out.println("Called calculateMaxWeight(" + i + ")");
            return maxWeight[i];
        }

        System.out.println("Called calculateMaxWeight(" + i + ")");

        // Base case: if i = 0, the maximum weight is the weight of the first task

        // Recursive case:
        // Case 1: task i is in the solution
        Double weight1 = taskArray[i].getWeight();
        int compatibleIndex = compatibility[i];
        if (compatibleIndex != -1) {
            weight1 += calculateMaxWeight(compatibleIndex);
        }else{
            System.out.println("Called calculateMaxWeight(" + compatibleIndex + ")");
        }

        // Case 2: task i is not in the solution
        Double weight2 = calculateMaxWeight(i - 1);
        if (i == 0) {
            maxWeight[i] = taskArray[0].getWeight();
            return maxWeight[i];
        }

        // Choose the maximum of the two cases
        maxWeight[i] = Math.max(weight1, weight2);
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        // YOUR CODE HERE


        // Add first task to planGreedy
        planGreedy.add(taskArray[0]);
        int lastAddedIndex = 0;

        // Iterate over remaining tasks and add them to planGreedy if they are compatible
        for (int i = 1; i < taskArray.length; i++) {
            if (taskArray[i].getStartTime().compareTo(taskArray[lastAddedIndex].getFinishTime()) >= 0) {
                planGreedy.add(taskArray[i]);
                lastAddedIndex = i;
            }
        }

        // Print the plan
        System.out.print("Greedy Schedule\n" +
                "---------------\n");
        for (Task task : planGreedy) {
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        }

        return planGreedy;
    }
}
