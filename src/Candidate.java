import java.util.*;

public class Candidate{
    private String name;
    private int pos;

    ArrayList<Ballot> candidateVotes = new ArrayList<>();

    Candidate(String n, int p){
        this.name = n;
        this.pos = p;
    }

    public void changeName(String name){
        this.name = name;
    }
 
    public String getName(){
        return name;
    }

    public int getNumVotes(){
        return candidateVotes.size();
    }
    
    public int getPOS(){
        return pos;
    }
    
    public void addVote(Ballot vote){
        candidateVotes.add(vote);
    }
    
    public void clear(){
        candidateVotes.clear();
    }
    
}