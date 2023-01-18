import java.util.*;
/**
 * @author Franky Yang
 */
public class Candidate{
    
    private String name;
    private int pos;

    ArrayList<Ballot> candidateVotes = new ArrayList<>();

    Candidate(String n, int p){
        this.name = n;
        this.pos = p;
    }

    /**
     * 
     * @param name description
     */
    public void changeName(String name){
        this.name = name;
    }

    /**
     * 
     * @return asldkfasl
     */
    public String getName(){
        return name;
    }

    /**
     *WHAT METHOD DOES
     * @return descrualdjla
     */
    public int getNumVotes(){
        return candidateVotes.size();
    }
    /**
     * 
     * @return and integer desclkj
     */
    public int getPOS(){
        return pos;
    }
    /**
     * 
     * @param vote
     */
    public void addVote(Ballot vote){
        candidateVotes.add(vote);
    }
    /**
     * 
     */
    public void clear(){
        candidateVotes.clear();
    }
    
}