package algorithms;

import lombok.Getter;
import utils.Interval;
import utils.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class JohnsonAlgorithm implements Algorithm {

    @Getter
    private List<Task> tasks;

    @Getter
    private List<Interval> firstMachineIntervals;
    @Getter
    private List<Interval> secondMachineIntervals;


    public JohnsonAlgorithm(List<Task> tasks) {
        this.tasks = tasks;
        firstMachineIntervals = new ArrayList<>();
        secondMachineIntervals = new ArrayList<>();
    }

    @Override
    public Object solve() {
        getTasksInCorrectOrder();
        computeTaskDurationOnEachMachine();

        int totalDuration = secondMachineIntervals.get(secondMachineIntervals.size() - 1).getUpperBound();
        System.out.println(totalDuration);
        tasks.forEach(task -> System.out.print(task.getNumber() + " "));


        return totalDuration;
    }

    private List<Task> getTasksWithShorterDuration() {
        List<Task> shorterTasks = new ArrayList<>();

        for (Task task : tasks) {
            int timeOnFirstMachine = task.getDurationOnFirstMachine();
            int timeOnSecondMachine = task.getDurationOnSecondMachine();

            if (timeOnFirstMachine < timeOnSecondMachine) {
                shorterTasks.add(task);
            }
        }

        shorterTasks.sort(Comparator.comparingInt(Task::getDurationOnFirstMachine));

        return shorterTasks;
    }

    private List<Task> getTasksWithLongerDuration() {
        List<Task> longerTasks = new ArrayList<>();

        for (Task task : tasks) {
            int timeOnFirstMachine = task.getDurationOnFirstMachine();
            int timeOnSecondMachine = task.getDurationOnSecondMachine();

            if (timeOnFirstMachine >= timeOnSecondMachine) {
                longerTasks.add(task);
            }
        }

        longerTasks.sort(Comparator.comparingInt(Task::getDurationOnSecondMachine).reversed());

        return longerTasks;
    }

    private List<Task> getTasksInCorrectOrder() {
        List<Task> correctOrderTasks = new ArrayList<>(getTasksWithShorterDuration());
        correctOrderTasks.addAll(getTasksWithLongerDuration());

        tasks = correctOrderTasks;
        return correctOrderTasks;
    }

    private void computeTasksDurationFirstMachine() {
        Interval firstInterval = new Interval(0, tasks.get(0).getDurationOnFirstMachine());
        firstMachineIntervals.add(firstInterval);

        for (int i = 1; i < tasks.size(); i++) {
            int lowerBound = firstMachineIntervals.get(i - 1).getUpperBound();
            int taskDuration = tasks.get(i).getDurationOnFirstMachine();
            int upperBound = lowerBound + taskDuration;
            Interval interval = new Interval(lowerBound, upperBound);
            firstMachineIntervals.add(interval);
        }
    }

    private void computeTasksDurationOnSecondMachine() {
        Interval firstInterval = new Interval(firstMachineIntervals.get(0).getUpperBound(), firstMachineIntervals.get(0).getUpperBound() + tasks.get(0).getDurationOnSecondMachine());
        secondMachineIntervals.add(firstInterval);
        for (int i = 1; i < tasks.size(); i++) {
            int lowerBound = firstMachineIntervals.get(i).getUpperBound();
            if (lowerBound < secondMachineIntervals.get(i - 1).getUpperBound()) {
                lowerBound = secondMachineIntervals.get(i - 1).getUpperBound();
            }
            int taskDuration = tasks.get(i).getDurationOnSecondMachine();
            int upperBound = lowerBound + taskDuration;

            secondMachineIntervals.add(new Interval(lowerBound, upperBound));
        }
    }


    public void computeTaskDurationOnEachMachine() {
        computeTasksDurationFirstMachine();
        computeTasksDurationOnSecondMachine();
    }
}
