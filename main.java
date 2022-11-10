import java.util.*;

public class Main{
    static int numCandidates;
    static int numVoters;
    static int numSeats;
    static double percentRequired = 50.00;
    
    
    public static void main (String[]args){
        Scanner scnr = new Scanner (System.in);
        
//ask how many candidates exist        
        System.out.println ("STV Voting");
        System.out.println ("How many candidates are there?");
        numCandidates = scnr.nextInt ();
        scnr.nextLine ();
        
        ArrayList <Candidate> candidateList = new ArrayList <> (numCandidates);

//going to etch this out to do an alternate vote system
/*        
//ask how many seats available        
        System.out.println ("How many seats are available?");
        numSeats = scnr.nextInt ();
        scnr.nextLine();
*/

//creates each candidate        
        for (int i = 0; i < numCandidates; ++i){
            System.out.println ("What is the name of candidate " + (i + 1) + "?");
            String name = scnr.nextLine ();
            Candidate temp = new Candidate (name, i);
            candidateList.add (temp);
        }
//going to etch this out to do an alternate vote system
/*
// calculates the Threshold
        percentRequired = (double) ((1.0 / numSeats) * 100);
*/

//CREATES EACH CANDIDATES POSSIBLE BALLOTS
        voteRankPath voteRankPath = new voteRankPath(numCandidates);
        
        for (int i = 0; i < candidateList.size(); ++i){
            candidateList.get(i).addBallotType(voteRankPath.getBallotTypes());
        }
        
//PRINTS OUT THE CANDIDATES NAME        
        for (int i = 0; i < numCandidates; ++i){
            System.out.println ("\t" + (i + 1) + ". " + candidateList.get(i).getName());
        }

//INPUTS HOW MANY VOTERS THERE ARE

        System.out.println ("Threshold: " + percentRequired);
        System.out.println();

        System.out.println ("How many voters are there?");
        numVoters = scnr.nextInt ();
        scnr.nextLine();
        
        scnr.close();

//CREATES THE RANDOM VOTERS
        ArrayList < Voter > voters = new ArrayList <> ();

        for (int i = 0; i < numVoters; ++i){
            Voter temp = new Voter ();
            voters.add (temp);
        }

//COUNTS AND ARRANGES THE TYPES OF BALLOT PER CANDIDATE
        for(int i = 0; i < voters.size(); ++i){
            for(int j = 0; j < candidateList.size(); ++j){
                candidateList.get(j).addBallotCount(voters.get(i).getBallot());
            }
        }
        
//AFTER EACH CANDIDATES VOTES ARE ORGANIZE EMPTIES VOTERS ARRAYLIST        
        voters.removeAll(voters);
        
//CALCULATES THE PERCENTAGE EACH CANDIDATE HAS        
        for(int i = 0; i < candidateList.size(); ++i){
            candidateList.get(i).setPercentage(((double)(candidateList.get(i).getCount1st())/(double)(numVoters))*100);
            System.out.println("\t" + candidateList.get(i).getName() + ": " + candidateList.get(i).getPercentage());
        }
        
//SORTS CANDIDATES BY PERCENTAGES
        for(int i = 0; i < candidateList.size()-1; ++i){
            int lowestPOS = i;
            
            for(int j = i+1; j < candidateList.size(); ++j){
                if (candidateList.get(j).getPercentage() < candidateList.get(i).getPercentage()){
                    lowestPOS = j;
                    
                }
            }
            
            Candidate temp = candidateList.get(lowestPOS);
            
            candidateList.set(lowestPOS,candidateList.get(i));
            candidateList.set(i, temp);
        }
        
        System.out.println();
        System.out.println("Sorted Candidate List:");
        
        for(int i = 0; i < candidateList.size(); ++i){
            System.out.println("\t" + candidateList.get(i).getName() + ": " + candidateList.get(i).getPercentage());
        }
        

        
    }
}
