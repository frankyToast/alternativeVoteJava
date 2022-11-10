import java.util.*;

public class Voter extends Main{
    private ArrayList<Integer> ballot = new ArrayList<Integer>();

    //Constructor for a voter for manual input
    public Voter(ArrayList<Integer> ballot){
        this.ballot = ballot;
    }
    
    
    //Constructor for a random voter ballot
    public Voter(){
        boolean validBallot = false;
        
        while(!validBallot){
            ArrayList<Integer> randomBallot = new ArrayList<>();
    
            for(int i = 0; i < numCandidates; i++){
                Random rand = new Random();
                int rank = rand.nextInt(numCandidates+1);
                randomBallot.add(rank);
            }
            
            this.ballot = randomBallot;
            
            Voter test = new Voter(randomBallot);
            validBallot = test.isValidBallot();
        }
        
    }
    
    //returns the ballot of a voter
    public ArrayList<Integer> getBallot(){
        return ballot;
    }
    
    //changes the ballot of a voter
    public void changeBallot(ArrayList<Integer> newBallot){
        this.ballot = newBallot;
    }

    //checks to see if ballot is valid
    public boolean isValidBallot(){
        ArrayList<Integer> testBallot = new ArrayList<>();

        int largestInt = 0;
        
        //checks ballot for the largest integer
        for(int i = 0; i < this.ballot.size(); ++i){
            if (this.ballot.get(i) > largestInt){
                largestInt = this.ballot.get(i);
            }
        }

        //if a ballot has all zero and if a ballot has a int larger than the num of reps
        //it would be an invalid ballot
        if (largestInt >= numSeats && largestInt < this.ballot.size()+1){
            
            //creates a template of arrayList of all the numbers the ballot should have
            testBallot.add(0, largestInt);
            for(int i = 1; i < this.ballot.size();++i){
                if (largestInt > 0){
                    largestInt--;
                } 
                testBallot.add(largestInt);
            }

            //checks to see if there is a value in testBallot that matches the voterBallot
            //removes the elements from both arryList if true
            for(int i = 0; i < this.ballot.size(); ++i){
                for(int j = 0; j < testBallot.size(); ++j){
                    if(this.ballot.get(i) == testBallot.get(j)){
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

    //prints the ballot
    public void printBallot(){
        for(Integer i: this.ballot){
            System.out.print(i + " ");
        }
        System.out.println();
    }
}
