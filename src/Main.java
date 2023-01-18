import java.util.*;
import java.io.*;
import customExceptions.*;

public class Main{
    static int numCandidates=0;
    static int numVoters;
    final static double PERCENT_REQUIRED = 0.50;
    static int countPOS = 0;
    ArrayList <Candidate> candidateList;

    /**
     * @param args
     * @throws IOException
     * @throws MinimumValueException
     * @throws InputMismatchException
     */
    public static void main (String[]args) throws IOException,MinimumValueException,InputMismatchException{

        Scanner scnr = new Scanner (System.in);
        
//ASK HOW MANY CANDIDATES EXIST, MUST BE AN INT AND GREATER THAN 1        
        System.out.println ("Alternative Vote");

        do{
            try{
                System.out.println("How many candidates are there?");
                numCandidates = scnr.nextInt();
                
                if(numCandidates<1){
                    throw new MinimumValueException(1);
                }
    
            }catch(MinimumValueException ex){
                System.out.println(ex.getMessage());
                
            }catch(InputMismatchException ex){
                System.out.println("[Error]: Must input an Integer");
                scnr.nextLine();
    
            }

        }while(numCandidates<=1);

        scnr.nextLine();

//CANIDATE CREATION / VERIFICATION
        String response = "N";
       // boolean correctCandidates = false;
        ArrayList <Candidate> candidateList = new ArrayList <>(numCandidates);
        do{
            //CREATES EACH CANDIDATE        
            for (int i = 0; i < numCandidates; ++i){
                System.out.println ("What is the name of candidate " + (i + 1) + "?");
                String name = scnr.nextLine ();
                Candidate temp = new Candidate (name,countPOS);
                countPOS++;
                candidateList.add (temp);
            }

            //VERIFES CANDIDATES NAME        
            for (int i = 0; i < numCandidates; ++i){
                System.out.println ("\t Candidate " + (candidateList.get(i).getPOS() + 1) + ": - " + candidateList.get(i).getName());
            }

            boolean correctResponse = false;
            do{
                try{
                    System.out.println("Is this correct?(Y/N)");
                    response = scnr.nextLine();
                    if(response.equals("Y") || response.equals("N") ){
                        correctResponse = true;
                    }else{
                        throw new ValidResponseException();
                    }
    
                }catch(ValidResponseException ex){
                    System.out.println(ex.getMessage());
                }

            }while(!correctResponse);

            if (response.equals("N")){
                candidateList.clear();
                countPOS = 0;
                System.out.println("[Clearing Candidate List]");
            }

        }while(!(response.equals("Y")));

//ASK USER FOR RANDOMLY GENERATED VOTES OR MANUAL INPUT VIA CSV FILE
        int voteChoice = 0;

        do{
            try{
                System.out.println("Would you like randomly generated votes(1) or manual inputed votes(2)?");
                 voteChoice = scnr.nextInt();
                if (voteChoice!=1 && voteChoice!=2){
                    throw new ValidResponseException();
                }
                
            }catch(ValidResponseException ex){
                System.out.println(ex.getMessage());
    
            }catch(InputMismatchException ex){
                System.out.println("[Error]: Must input an Integer");
                scnr.nextLine();
            }    

        }while(voteChoice!=1 && voteChoice!=2);
        
        ArrayList<Ballot> voters = new ArrayList<>(); 

        if (voteChoice == 1){
            voters = randomlyGeneratedVotes();
        } else{
            voters = manualInputeVotes();
        }

        scnr.close();

//START OF THE VOTE CALCULATIONS
        boolean loop = true;
        int winningCandidate = -1;
        int eliminatedCount = 0;
        
        while(loop){
            eliminatedCount = 0;
        
            //ARRANGES THE VOTES ACCORDING TO THEIR CANDIDATE
            for (int i = 0; i < numCandidates; ++i){
                for (int j = 0; j < numVoters; ++j){
                    if(voters.get(j).getBallot().get(i)==1){
                        candidateList.get(i).addVote(voters.get(j));
                    }
                }
            }
            
            //PRINTS THE RESULTS
            System.out.println("\nResults: ");
            
            //PRINTS OUT THE CANDIDATE AND THEIR CURRENT PERCENTAGE
            //ALSO CHECKS IF ANY CANDIDATE HAS 50% && COUNTS THE NUMBER OF CANDIDATES ELMINATED
            for (int i = 0; i < numCandidates; ++i){
                double candidatePercent = (double)candidateList.get(i).getNumVotes()/(double)numVoters;
                System.out.println("\t"+candidateList.get(i).getName() + ": " + candidatePercent);
                
                if(candidatePercent == 0){
                    eliminatedCount++;
                }
                
                if(candidatePercent >= PERCENT_REQUIRED){
                    winningCandidate = i;
                    loop = false;
                }
            }
            
            //CHECKS IF THE NUMBER OF ELIMINATED CANDIADTES IS EQUAL TO 1-numCandidates
            if(eliminatedCount == numCandidates-1){
                loop = false;
            }
            
            //IF NONE OF THE CANDIDATE MEETS THE REQUIREMENTS, THE LOWEST IS ELIMINATED
            //(IF 2+ ARE THE LOWEST IT WILL BE RANDOMLY CHOOSEN)
            //THE LOWEST CANDIDATES VOTES WILL GO INTO THE voters ARRAYLIST AND BE REORGANIZED
            if (loop){
                ArrayList<Integer> lowestCandidates = new ArrayList<>();
                
                //FINDS THE CANDIDATE WITH THE LOWEST PERCENTAGE
                double lowestPercent = 0.50;
                
                for (int i = 0; i < numCandidates; ++i){
                    double candidatePercent = (double)candidateList.get(i).getNumVotes()/(double)numVoters;
                    if(candidatePercent < lowestPercent && candidatePercent > 0){
                        lowestPercent = (double)candidateList.get(i).getNumVotes()/(double)numVoters;
                    }
                }
                
                for (int i = 0; i < numCandidates; ++i){
                    double candidatePercent = (double)candidateList.get(i).getNumVotes()/(double)numVoters;
                    if(candidatePercent == lowestPercent){
                        lowestCandidates.add(i);                        
                    }
                }
                
                //NEED TO ADD LOOP TO CHECK FOR OTHER CANDIDATES WITH THE SAME PERCENTAGE
                System.out.println("Lowest Candidate(s): "+ lowestCandidates + "\tSize:" + lowestCandidates.size());
                
                //RANDOMLY CHOOSES A POS TO ELIMINATE IF THERE ARE MULTIPLE OF THE SAME
                int eliminatedPOS = (int)(Math.random()*lowestCandidates.size());
                eliminatedPOS = lowestCandidates.get(eliminatedPOS);
                System.out.println("Candidate Elminated POS: " + eliminatedPOS);
                
                for(int i = 0; i < numVoters; ++i){
                    if(eliminatedCount < numCandidates-1){
                        voters.get(i).reduceByPOS(eliminatedPOS);
                    }
                    //System.out.println(voters.get(i));
                }
                
                //IF NONE ARE WINNING CANDIDATE, IT CLEARS THE VOTES FROM EACH CANDIDATE
                for (int i = 0; i < numCandidates; ++i){
                    candidateList.get(i).clear();
                }
                
                System.out.println("Candidates Eliminated: " + eliminatedCount);
            }   
        }
        
        System.out.println("Final Results: ");
        System.out.println("Voter Size:" + voters.size());
        
        for (int i = 0; i < numCandidates; ++i){
            double candidatePercent = (double)candidateList.get(i).getNumVotes()/(double)numVoters;
            System.out.println(candidateList.get(i).getName() + ": " + candidatePercent);
        }
        
        System.out.println("Winner of Alternative Vote: " + candidateList.get(winningCandidate).getName());
    }

//METHODS USED TO RANOMLY GENERATE VOTES    
    public static ArrayList<Ballot> randomlyGeneratedVotes(){
        ArrayList<Ballot> voters = new ArrayList<>(numVoters);
        Scanner scnr = new Scanner(System.in);

        //ASK NUMBER OF VOTES FOR RANDOMLY GENERATES VOTES
        do{
            try{
                System.out.println ("How many voters are there?");
                numVoters = scnr.nextInt ();
                if(numVoters<10){
                    throw new MinimumValueException(10);
                }

            }catch(MinimumValueException ex){
                System.out.println(ex.getMessage());
            
            }catch(InputMismatchException ex){
                System.out.println("[Error]: Must input an Integer");
                scnr.nextLine();
            }
        }while(numVoters<10);
        
        scnr.close();
        
        //CREATES THE RANDOM VOTERS
        for (int i = 0; i < numVoters; ++i){
            Ballot vote = new Ballot (numCandidates);
            voters.add(vote);
        }  
        
        return voters;
    }

    public static ArrayList<Ballot> manualInputeVotes() throws FileNotFoundException{
        ArrayList<Ballot> voters = new ArrayList<>();
        
        System.out.println("To put in manual votes edit the 'votes.csv' file");
        System.out.println("Each row represents a singular ballot");
        System.out.println("Each candidate has a unique position(column), rank each candidate accordingly");
                
        Scanner scnr = new Scanner(new File("Alternative_Vote\\src\\votes.csv"));

        while(scnr.hasNext()){
            String line = scnr.nextLine();
            String[] ballot = line.split(",");

            ArrayList<Integer> list = new ArrayList<>();

            for(int i = 0; i < numCandidates; ++i){
                int rank = Integer.valueOf(ballot[i]);
                list.add(rank);
            }
            
            Ballot temp = new Ballot(list);
            voters.add(temp);
        }

        numVoters = voters.size();
        scnr.close();
        return voters;
    }
}