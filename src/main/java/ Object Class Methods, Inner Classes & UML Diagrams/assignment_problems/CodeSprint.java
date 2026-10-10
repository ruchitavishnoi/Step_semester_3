import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

interface Track {
    String getName();
    double calculateFinalScore(double idea, double execution, double presentation);
}

class InnovationTrack implements Track {
    @Override
    public String getName() {
        return "Innovation track";
    }

    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenTrack implements Track {
    @Override
    public String getName() {
        return "Open track";
    }

    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private String id;
    private String name;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Score {
    private double idea;
    private double execution;
    private double presentation;

    public Score(double idea, double execution, double presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public double getIdea() {
        return idea;
    }

    public double getExecution() {
        return execution;
    }

    public double getPresentation() {
        return presentation;
    }
}

class Project {
    private String title;
    private Score score;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}

class Team {
    private String teamName;
    private List<Student> members;
    private Track track;
    private Project project;

    public Team(String teamName, List<Student> members, Track track) {
        this.teamName = teamName;
        this.members = members;
        this.track = track;
    }

    public String getTeamName() {
        return teamName;
    }

    public List<Student> getMembers() {
        return members;
    }

    public Track getTrack() {
        return track;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public double getFinalScore() {
        if (project == null || project.getScore() == null) {
            return 0.0;
        }
        Score s = project.getScore();
        return track.calculateFinalScore(s.getIdea(), s.getExecution(), s.getPresentation());
    }
}

enum HackathonState {
    OPEN,
    JUDGING,
    PUBLISHED
}

class Hackathon {
    private String name;
    private List<Team> teams;
    private Set<String> registeredStudentIds;
    private HackathonState state;

    public Hackathon(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.registeredStudentIds = new HashSet<>();
        this.state = HackathonState.OPEN;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public boolean registerTeam(String teamName, List<Student> members, Track track) {
        if (members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student s : members) {
            if (registeredStudentIds.contains(s.getId())) {
                System.out.println("Registration failed: Student " + s.getName() + " is already registered in another team.");
                return false;
            }
        }

        for (Student s : members) {
            registeredStudentIds.add(s.getId());
        }

        Team team = new Team(teamName, members, track);
        teams.add(team);
        System.out.println("Team " + teamName + " registered (" + members.size() + " members, " + track.getName() + ").");
        return true;
    }

    public boolean submitProject(Team team, String projectTitle) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Submission failed: Results have already been published.");
            return false;
        }
        if (team.getProject() != null) {
            System.out.println("Submission failed: Team already submitted a project.");
            return false;
        }
        Project project = new Project(projectTitle);
        team.setProject(project);
        System.out.println("Project '" + projectTitle + "' submitted by " + team.getTeamName() + ".");
        return true;
    }

    public boolean scoreProject(Project project, double idea, double execution, double presentation, Team team) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published.");
            return false;
        }
        Score score = new Score(idea, execution, presentation);
        project.setScore(score);
        double finalScore = team.getFinalScore();
        System.out.printf("Score recorded for '%s'. Final score: %.2f.%n", project.getTitle(), finalScore);
        return true;
    }

    public void publishResults() {
        this.state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }
}

public class CodeSprint {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        Student asha = new Student("S1", "Asha");
        Student ravi = new Student("S2", "Ravi");
        Student neha = new Student("S3", "Neha");
        List<Student> byteBustersMembers = new ArrayList<>();
        byteBustersMembers.add(asha);
        byteBustersMembers.add(ravi);
        byteBustersMembers.add(neha);

        hackathon.registerTeam("ByteBusters", byteBustersMembers, new InnovationTrack());

        Student kiran = new Student("S4", "Kiran");
        List<Student> soloMembers = new ArrayList<>();
        soloMembers.add(kiran);

        hackathon.registerTeam("SoloCoder", soloMembers, new OpenTrack());

        Team byteBusters = hackathon.getTeams().get(0);
        hackathon.submitProject(byteBusters, "SmartAttend");

        Project smartAttend = byteBusters.getProject();
        hackathon.scoreProject(smartAttend, 8, 7, 9, byteBusters);

        hackathon.publishResults();

        hackathon.scoreProject(smartAttend, 10, 7, 9, byteBusters);
    }
}
