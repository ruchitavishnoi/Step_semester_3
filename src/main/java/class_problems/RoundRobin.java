import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;
import java.util.List;

class Job {
    String name;
    int remainingTime;

    public Job(String name, int remainingTime) {
        this.name = name;
        this.remainingTime = remainingTime;
    }
}

class LabScheduler {
    public static void schedule(int quantum, Job[] initialJobs) {
        Queue<Job> queue = new LinkedList<>();
        for (Job job : initialJobs) {
            queue.offer(new Job(job.name, job.remainingTime));
        }

        int currentTime = 0;
        StringBuilder slices = new StringBuilder();
        List<String> finished = new ArrayList<>();

        while (!queue.isEmpty()) {
            Job currentJob = queue.poll();
            int timeToRun = Math.min(quantum, currentJob.remainingTime);
            
            int startTime = currentTime;
            currentTime += timeToRun;
            currentJob.remainingTime -= timeToRun;
            
            if (slices.length() > 0) {
                slices.append(" ");
            }
            slices.append(currentJob.name).append("(").append(startTime).append("-").append(currentTime).append(")");
            
            if (currentJob.remainingTime > 0) {
                queue.offer(currentJob);
            } else {
                finished.add(currentJob.name + " " + currentTime);
            }
        }

        System.out.println(slices.toString());
        System.out.print("finish ");
        for (int i = 0; i < finished.size(); i++) {
            System.out.print(finished.get(i));
            if (i < finished.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int quantum = 3;
        Job[] jobs = {
            new Job("P1", 5),
            new Job("P2", 3),
            new Job("P3", 7)
        };
        schedule(quantum, jobs);
    }
}
