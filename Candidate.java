import java.util.*;
import java.util.function.DoubleUnaryOperator;

public class Candidate{
    private int pos;
    private String name;
    int count1st = 0;
    private boolean isSelected;
    private double percent1st;

    ArrayList<ArrayList<Integer>> ballotTypes = new ArrayList<>();
    ArrayList<Integer> countPerBallotType = new ArrayList<>();
    
    
//constructor
    Candidate(String name, int pos){
        this.name = name;
        this.pos = pos;
        this.isSelected = false;
    }
    
//setter for name
    public void changeName(String name){
        this.name = name;
    }
//getter for name    
    public String getName(){
        return name;
    }
//setter for percent1st
    public void setPercentage(double percentage){
        this.percent1st = percentage;
    }
    
//getter for percent1st    
    public double getPercentage(){
        return percent1st;
    }
    
//setter for count1st    
    public void setCount1st(int count){
        this.count1st = count;
    }
    
//getter for count1st    
    public int getCount1st(){
        return count1st;
    }
    
//adds a ballot type for a Candidate    
    public void addBallotType(ArrayList<ArrayList<Integer>> ballots){
	    for(int i = 0; i < ballots.size(); ++i){
	        if(ballots.get(i).get(pos) == 1){
	            ballotTypes.add(ballots.get(i));
	            countPerBallotType.add(0);
	        }
	    }
	    
    }
    
//views the ballot type for each Candidate    
    public void viewBallotTypes(){
	    for(int i = 0; i < ballotTypes.size(); ++i){
	        for(int j = 0; j < ballotTypes.get(i).size(); ++j){
	            System.out.print(ballotTypes.get(i).get(j) + " ");
	        }
	        System.out.println();
	    }
    }
    
//gets the ballots that each candidate would possibly have
    public ArrayList<ArrayList<Integer>> getBallotTypes(){
        return ballotTypes;
    }


//checks the voter ballot and adds the count to the canddiate ballot type if it matches     
    public void addBallotCount(ArrayList<Integer> ballot){

        for(int i = 0; i < ballotTypes.size(); ++i){
            boolean matchingArray = false;
            matchingArray = isArraysMatch(ballot, ballotTypes.get(i));
            
            if(matchingArray == true){
                countPerBallotType.set(i,countPerBallotType.get(i) + 1);
                count1st++;
            }
        }
        
    }

//checks to see if two arrays have the same integer in the same order    
    public boolean isArraysMatch (ArrayList<Integer> srcList, ArrayList<Integer> checkList){
	    int count = 0;
	    
	    for(int i = 0; i < srcList.size(); ++i){
	        if (srcList.get(i) == checkList.get(i)){
	            count++;
	        }
	    }
	    
	    if (count == srcList.size()){
	        return true;
	    } else{
	        return false;
	    }
	}
	
//counts how many 1st vote ballots a candidate has 
	public int calc1stVotes(){
	    for(int i = 0; i < countPerBallotType.size(); ++i){
	        count1st+= countPerBallotType.get(i);
	    }
	    return count1st;
	}
	
	public ArrayList<ArrayList<Integer>> ballotListMinus1(Candidate candidate){
        ArrayList<ArrayList<Integer>> ballotListMinus1 = new ArrayList<>();
        
        //gets each ballot type
        for(int i = 0; i < candidate.ballotTypes.size(); ++i){
            ArrayList<Integer> temp = new ArrayList<>();
            
            //makes modifies the candidate ballots as if the candidate was eliminated 
            for(int j = 0; j < candidate.ballotTypes.get(i).size(); ++j){
                if(candidate.ballotTypes.get(i).get(j) ==1 ||candidate.ballotTypes.get(i).get(j) == 0){
                    temp.add(0);
                } else{
                    temp.add(candidate.ballotTypes.get(i).get(j)-1);
                }       
            }
            //adds the new ballot type to the list
            ballotListMinus1.add(temp);
            
        }
        
        return ballotListMinus1;
    }


}
