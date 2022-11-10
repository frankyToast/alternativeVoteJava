import java.util.*;

public class voteRankPath{
    ArrayList<ArrayList<Integer>> ballotTypes = new ArrayList<>();

//creates all the possible ways a voter can vote    
    voteRankPath(int num){
        Factorial fact = new Factorial();
        int numPos = fact.calcFact(num);
        
        for(int i = 1; i < num; ++i){
            numPos += fact.divideFact(num,i);
	    }
        
        //creates the different possibilities 
	    Random rand = new Random();

        for(int j = 0; j < numPos; ++j){
            ArrayList<Integer> ballot = new ArrayList<>();
            boolean isValid = false;

            while(!isValid){
                ballot.clear();
                
        //makes the first ArrayList            
    	        for(int i = 0; i <num; ++i){
    	            int temp = rand.nextInt(num+1);
    	            ballot.add(temp);
    	        }
        //verifies that the list does not repeat numbers within itself 
                boolean noRepeats = isRepeatExceptZero(ballot);
      
        //verifiest that list array is not the same as any in list2D
                
                boolean isMatching = false;
                for(int i = 0; i < ballotTypes.size(); ++i){
                    isMatching = isArraysMatch(ballotTypes.get(i), ballot);
                    if(isMatching){
                        break;
                    }
                }
                
                if(!noRepeats && !isMatching){
                    isValid = true;
                }
            }
        //adds the array to list2D
	        ballotTypes.add(ballot);
        }
        
    }
    
//checks to see if the srcList is in checkList	
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

// used to check if a list repeats numbers other than zero	
	public boolean isRepeatExceptZero(ArrayList<Integer> srcList){
	    
        ArrayList<Integer> testList = new ArrayList<>();
    //test to see what the max int is 
	    int maxInt = 0;
	    
	    for(int i = 0; i < srcList.size(); ++i){
	        if (srcList.get(i) > maxInt){
	            maxInt = srcList.get(i);
	        }
	    }
	    
    // makes a testList that starts with the max int decreases by 1 until zero 
    // then repeats zero 
	    for(int i = 0; i < srcList.size(); ++i){
	        if ((maxInt-i) >= 0){
	            testList.add((maxInt-i));
	        } else if((maxInt-i) < 0){
	            testList.add(0);
	        }
	    }

        for(int i = 0; i < srcList.size(); ++i){
            for(int j = 0; j < testList.size(); ++j){
                if(srcList.get(i) == testList.get(j)){
                    testList.remove(j);
                    break;
                }
            }
            
        }
	    
	    if(maxInt == 0){
	        return true;
	    }else if(testList.isEmpty()){
	        return false;
	    } else{
	        return true;
	    }
	    
	}
	
	public  ArrayList<ArrayList<Integer>> getBallotTypes(){
	    return ballotTypes;
	    
	}
}
