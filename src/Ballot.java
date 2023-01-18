import java.util.*;

public class Ballot{
    ArrayList<Integer> ballot = new ArrayList<Integer>();

    //Constructor for a voter for manual input
    public Ballot(ArrayList<Integer> vote){
        if(checkBallot(vote)){
            this.ballot = vote;
        }
    }
    
    //Constructor for a random voter ballot
    public Ballot(int candidates){
        boolean validBallot = false;
        ArrayList<Integer> randomBallot = new ArrayList<>(candidates);
        
        while(!validBallot){
            
            randomBallot.clear();
    
            for(int i = 0; i < candidates; i++){
                int rank = (int)(Math.random()*(candidates+1));
                randomBallot.add(rank);
            }  
            
            validBallot = checkBallot(randomBallot);
        }
        
        this.ballot = randomBallot;
        
    }
    
    //returns the ballot of a voter
    public ArrayList<Integer> getBallot(){
        return this.ballot;
    }

    //checks to see if ballot is valid
    public boolean checkBallot(ArrayList<Integer> tempBallot){
        ArrayList<Integer> testBallot = new ArrayList<>();

        int largestInt = 0;
        
        //checks ballot for the largest integer
        for(int i = 0; i < tempBallot.size(); ++i){
            if (tempBallot.get(i) > largestInt){
                largestInt = tempBallot.get(i);
            }
        }

        //if a ballot has all zero and if a ballot has a int larger than the num of reps
        //it would be an invalid ballot
        if (largestInt > 0 && largestInt < tempBallot.size()+1){
            
            int counter = largestInt;
            //creates a template of arrayList of all the numbers the ballot should have
            for(int i = 0; i < tempBallot.size();++i){
                if (counter > 0){
                    testBallot.add(counter);
                    counter--;
                } else{
                    testBallot.add(0);
                }
            }

            //checks to see if there is a value in testBallot that matches the voterBallot
            //removes the elements from both arryList if true
            for(int i = 0; i < tempBallot.size(); ++i){
                for(int j = 0; j < testBallot.size(); ++j){
                    if(tempBallot.get(i) == testBallot.get(j)){
                        testBallot.remove(j);
                        break;
                    }    
                }
            }

            //if both ballots are empty, it means that it is a valid ballot 
            if(testBallot.isEmpty()){
                return true;
            }
        }

        return false;
    }
    
    public void reduceByPOS(int pos){
        int value = this.ballot.get(pos);
        
        this.ballot.set(pos,0);
        if (value > 0){
            for(int i = 0; i < this.ballot.size(); ++i){
                if(this.ballot.get(i) > value){
                    this.ballot.set(i,this.ballot.get(i)-1);
                }
            }
        }
    }

    //prints the ballot
    public String toString(){
        String arrayString = "";
        
        for(int i = 0; i < ballot.size();++i){
            arrayString += ballot.get(i);
            arrayString += " ";
        }
        return arrayString + "\n";
    }
}