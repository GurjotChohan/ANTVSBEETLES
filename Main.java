// Name: Gurjot K Chohan

/***********************************************************************************************************************
* Last Modified: Sep 27 2022 3:00 PM
* Program Function: In this porgram we are dispaying the board of an ant vs beetles games after each turn up until the turns specified 
* by the user. we are using preset rules of how the ant or beetle are suppose to move given their surroundings to genrate the board
* for each turn. 
* **********************************************************************************************************************/

//make sure that functions are not capitalized
import java.io.*;
import java.util.Scanner;                           // import the Scanner class
import java.util.Arrays;
import javax.swing.*; 


public class Main {

    public static void main(String[] args) throws IOException {
        
        String filename; 
        String line;
        int linenum=0;
        int numofturns=0;                           // store the number of turns specified by the user
        String encounterdobj;                       // store the string that provided infromation on the ant or beetles surroundings

        char letterant; 
        char letterBeetle; 
        Scanner input = new Scanner(System.in);     // Create a Scanner object
        System.out.println("Enter input file:");    // prompting for file name
        filename = input.nextLine();                // read in the filename
        
        System.out.println("Enter the letter of ants"); // prompting for the letter to represent ant
        letterant=input.next().charAt(0);           //read in the ant char
        
        System.out.println("Enter the letter of Beetles"); // prompting for the letter to represent beetle
        letterBeetle=input.next().charAt(0);        //read in the beetle char
        
        System.out.println("Enter the number of turns"); // prompting for the number of turns the program should run
        numofturns = input.nextInt();               //read in the number of turns
        
        input.close();                              // close input scanner

        File inFile = new File(filename);           // A File object created by passing in filename
        Scanner in = new Scanner(inFile);           // a new scanner object created to read in the file data
        Creature grid[][]= new Creature[10][10];    // a creature class grid created with 10 rows and 10 col


        if(inFile.canRead()){                       //checks to see if file opened properly. returns true if it did false otherwise
            while (in.hasNext()){                   //while not at the end of the file
                line = in.nextLine();               // read the fileline into the string varaible line
                
                for(int i=0; i<line.length(); i++){ // for loop to create child objects according to the char charcter in the line
                    if(line.charAt(i) == '+'){
                        grid[linenum][i]=new Ant(); // create an ant class if the char is an a
                    }
                    else if (line.charAt(i) == '-'){
                        grid[linenum][i]=new Beetle(); // create an beetle class if the char is a B
                    }

                }
                linenum++;                          // update the line num to go to the next line of the grid
            } 
            in.close(); // close the file
            JPanelGrid GUI; 
            JFrame frame = new JFrame("ANTS VS. BEETLES");
            

            for(int turn=1; turn<=numofturns; turn++) // create a for loop for the number of turns
            {
    
                // beetles move
                for(int col=0; col<10; col++){        // for loop to go through the grid to move beetles
                    for(int row=0; row<10; row++){
                        // check if the object is a beetle and the member varible has moved is set to false
                        if((grid[row][col] instanceof Beetle) && grid[row][col].hasMovedorBreed==false){
                            encounterdobj= EncounterObjFunct(grid, row, col);  // call the EncounterObjFunct to get the locations of the nearest ants
                            encounterdobj=  encounterdobj+Ajacentants(grid, encounterdobj, row, col); // call the Ajacentants funtion to find the info about the ajacent ants and concat the string with encounterobj
                            movefunction(grid, grid[row][col].Move(encounterdobj), row, col); // get the direction to move from the class methord and then call the move function to make the movement if possible
                        }
                    }
                }

                // ants move
                for(int col=0; col<10; col++){ // for loop to go through the grid to move ants
                    for(int row=0; row<10; row++)
                        // check if the object is a ant and the member varible hasMovedorBreed is set to false
                        if((grid[row][col] instanceof Ant) && grid[row][col].hasMovedorBreed==false)
                            movefunction(grid, grid[row][col].Move(EncounterObjFunct(grid, row, col)), row, col); // get the direction to move from the class methord and then call the move function to make the movement if possible
                }
                hasmovedtofalse(grid); // reset all the objects hasmoved to false
                
                // beetles starve
                if(turn>=5)
                {
                    for(int col=0; col<10; col++){ // for loop to go through the grid to check if a beetle has straved
                        for(int row=0; row<10; row++){
                            // check if the object is a beetle and if is a result of perviously bread beetle
                            if((grid[row][col] instanceof Beetle)){
                                Beetle b= (Beetle) grid[row][col]; // narrowcasting or implicit casting
                                if(b.returnturnssinceeat()>= 5)// if the turnsscience eat is greater than or equal to 5 set the obejct to null
                                    grid[row][col]= null; 
                            }
                        }
                    }
                }
    
                // ant breed every three turns 
                if(turn%3==0 && !(turn==0)) {
                        for(int col=0; col<10; col++) // for loop to go through the grid to breed ants
                            for(int row=0; row<10; row++)
                                if((grid[row][col] instanceof Ant) && grid[row][col].hasMovedorBreed==false) 
                                    breedfunction(grid, row, col); // call the breedfunction
                }
                
                // beetles breed every eigth turns
                if(turn%8==0 && !(turn==0)){
                    for(int col=0; col<10; col++) // for loop to go through the grid to breed beetles
                        for(int row=0; row<10; row++)
                            if((grid[row][col] instanceof Beetle) && grid[row][col].hasMovedorBreed==false)
                                breedfunction(grid, row, col); // call the breedfunction
                }
                hasmovedtofalse(grid);

                // print out the turn grid
                System.out.println("TURN " + turn);
                for(int i=0; i<10; i++)
                {
                    for(int j=0; j<10; j++) // for loop to go through all the grid
                    {
                        if(grid[i][j] instanceof Beetle)
                            System.out.print(letterBeetle); // print the letter for beetle if object is beetle
                        else if(grid[i][j] instanceof Ant)
                            System.out.print(letterant); // print the letter for ant if object is ant
                        else
                            System.out.print(" "); // print nothing otherwise
                    }
                    System.out.println(); // print newline 
                }
                System.out.println(); // print newline 
                GUI=  new JPanelGrid(grid); 
        
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(GUI);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                try {
                    Thread.sleep(5000);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
                frame.remove(GUI);
            }
        }
    }

    public static void hasmovedtofalse(Creature grid[][])
    {
        for(int col=0; col<10; col++){ // go through the whole grid
            for(int row=0; row<10; row++){
               if(grid[row][col] instanceof Creature) // if ant or beetle
                  grid[row][col].hasMovedorBreed= false; // set hasMovedorBreed to false
            }
    }
    }
    public static String EncounterObjFunct(Creature grid[][], int objrow, int objcol)
    {
       char[] chars = {'0','0','0','0'}; // create a chars array initilized to '0'
        
        
        for(int i=objrow; i>=0; i--) // check the north side of the object 
        {
            // if the object is a ant then check for beetles to the north of it
            if((grid[objrow][objcol] instanceof Ant) && grid[i][objcol] instanceof Beetle)
               { chars[0] = (char)((objrow-i) +'0'); // if a beetle is found record the distance between the beetle and ant
                break; }
            // if the object is a beetle then check for ants to the north of it
            else if((grid[objrow][objcol] instanceof Beetle) && grid[i][objcol] instanceof Ant)
                {chars[0] = (char)((objrow-i) +'0'); // if a ant is found record the distance between the two
                break;}
        }

        for(int i= objcol; i<10; i++) // check the east side of the object
        {
            // if the object is a ant then check for beetles to the east of it
            if((grid[objrow][objcol] instanceof Ant) && grid[objrow][i] instanceof Beetle)
                {chars[1] = (char)((i-objcol) +'0'); // if a beetle is found record the distance between the beetle and ant
                break;}
            // if the object is a beetle then check for ants to the east of it
            else if((grid[objrow][objcol] instanceof Beetle) && grid[objrow][i] instanceof Ant)
                {chars[1] = (char)((i-objcol) +'0'); // if a ant is found record the distance between the two
                break;}
        }

        for(int i=objrow; i<10; i++) // check the south side of the object
        {
            // if the object is a ant then check for beetles to the south of it
            if((grid[objrow][objcol] instanceof Ant) && grid[i][objcol] instanceof Beetle)
                {chars[2] = (char)((i-objrow) +'0'); // if a beetle is found record the distance between the beetle and ant
                break;}
            // if the object is a beetle then check for ants to the south of it
            else if((grid[objrow][objcol] instanceof Beetle) && grid[i][objcol] instanceof Ant)
                {chars[2] = (char)((i-objrow) +'0'); // if a ant is found record the distance between the two
                break;}
        }

        for(int i= objcol; i>=0; i--) // check the west side of the object
        {
            // if the object is a ant then check for beetles to the west of it
            if((grid[objrow][objcol] instanceof Ant) && grid[objrow][i] instanceof Beetle)
                {chars[3] = (char)((objcol-i) +'0'); // if a beetle is found record the distance between the beetle and ant
                break;}
            // if the object is a beetle then check for ants to the west of it
            else if((grid[objrow][objcol] instanceof Beetle) && grid[objrow][i] instanceof Ant)
                {chars[3] = (char)((objcol-i) +'0'); // if a ant is found record the distance between the two
                break;}
        }
        //encounteredobj= String.valueOf(chars); 
        return String.valueOf(chars);
    }

    public static String Ajacentants(Creature grid[][], String EncounterObj, int row, int col)
    {
        int antrow= row; 
        int antcol= col; 
        int[] arr= {-1,0,1}; 
        int [] ajacent= new int [4]; 
        String s; 
        for (int i=0; i<4; i++)
        {
            antrow= row; 
            antcol= col;

            if(!(EncounterObj.charAt(i) == '0')) // if there is an ant present
            {
                // go to the ants location using the EncounterObj array
                if (i==0)
                    antrow=row-(EncounterObj.charAt(0)-'0'); // if in index 0 move north the distance
                else if (i==1)
                    antcol= col+(EncounterObj.charAt(1)-'0'); // if in index 1 move east the distance
                else if (i==2)
                    antrow = row+(EncounterObj.charAt(2)-'0'); // if in index 2 move south the distance
                else if (i==3)
                    antcol= col-(EncounterObj.charAt(3)-'0'); // if in indec 3 move west the distance
                
                
                for(int k=0; k<arr.length; k++)
                    for (int j=0; j<arr.length; j++)
                    {
                        //make sure that the row and col of the potential ajacent ant is valid between 0 to 9
                        if((antrow+arr[k])>=0 && (antrow+arr[k])<=9 && (antcol+arr[j])>=0 && (antcol+arr[j])<=9 && (grid[antrow+arr[k]][antcol+arr[j]] instanceof Ant))
                            //if (grid[antrow+arr[k]][antcol+arr[j]] instanceof Ant)
                                ajacent[i]++;
                    }
            }
        }
        s= Arrays.toString(ajacent); // turn int array into a string
        s=s.replace(",", ""); //remove the commas
        s=s.replace("[", ""); //remove the right bracket
        s=s.replace("]", ""); //remove the left bracket
        s=s.replace(" ", ""); // remove any spaces
        s=s.trim(); 
        return s; 

    }

    public static void movefunction(Creature grid[][], int movement, int objrow, int objcol)
    {
        int distanceedge[]= {objrow, 9-objcol, 9-objrow, objcol}; // check distance from the edge
        int farthestedge=0; // varaible to store the farthest edge
        int farthestedgeindex=0; // varible to store the index of the farthest edge
        int moveintorow= objrow; // to set the moveinrow
        int moveintocol= objcol; // to set the moveintocol
        boolean atedge=false; // check for edge

        // if -1 is returned and the object is a beetle find the farthest edge
        if(movement==-1 && grid[objrow][objcol] instanceof Beetle)
        { 
            // go through the distanceedge array and find the highest value
            for (int i=0; i<distanceedge.length; i++)
            {
                if(distanceedge[i]>farthestedge){
                    farthestedge= distanceedge[i];
                    farthestedgeindex= i; // store the index of the highest value
                }
            }
            movement=farthestedgeindex; // farthestedgeindex is the movement direction
        }

        // set the values of rows and columes depending on the movement direction
        // also check set the atedge value
        if(movement==0)
        {   
            moveintorow= objrow-1; 
            atedge = (objrow==0); 
        }
        else if(movement==1)
        {   
            moveintocol= objcol+1;
            atedge= (objcol==9);  
        }
        else if(movement==2)
        {   
            moveintorow= objrow+1; 
            atedge= (objrow==9); 
        }
        else if(movement==3)
        {   
            moveintocol= objcol-1; 
            atedge= (objcol==0); 
        }
     

        // if movement varible is valid
        if(movement==0 || movement==1 || movement==2 || movement==3)
        {
            if((grid[objrow][objcol] instanceof Beetle)) // check if the object is an beetle
            {
                Beetle b= (Beetle) grid[objrow][objcol];  // narrowcasting or implicit casting
                b.Starve(grid[moveintorow][moveintocol] instanceof Ant);  // call the starve to function with a argument true (if there is ant) else (false)
                grid[objrow][objcol] = b; // copy the object b grid[objrow][objcol] 

                if(!(atedge) && !(grid[moveintorow][moveintocol] instanceof Beetle)){ // if atedge is neagtive and there is no beetle in the move in location
                    grid[moveintorow][moveintocol] = grid[objrow][objcol]; // copy current beetle into the new location
                    grid[objrow][objcol]= null; // set the current location to null
                    grid[moveintorow][moveintocol].hasMovedorBreed=true; //  set the hasMovedorBreed to true
                }
            }
            //check if the object is an ant if atedge is neagtive and there is no beetle in the move in location
            else if((grid[objrow][objcol] instanceof Ant) && !(atedge) && !(grid[moveintorow][moveintocol] instanceof Ant)) 
            {
                grid[moveintorow][moveintocol] = grid[objrow][objcol]; // copy current ant into the new location
                grid[objrow][objcol]= null; // set the current location to null
                grid[moveintorow][moveintocol].hasMovedorBreed=true; //  set the hasMovedorBreed to true
            }
        }    

    }

    public static void breedfunction(Creature grid[][], int row, int col)
    {

        if((!(row==0) && ! (grid[row-1][col] instanceof Creature))){
            grid[row-1][col]= grid[row][col].Breed(); // call the breed function which return an object of its class
            grid[row-1][col].hasMovedorBreed= true; // set the hasMovedorBreed to true for the new object
        }
        else if((!(col==9) && ! (grid[row][col+1] instanceof Creature))){
            grid[row][col+1]= grid[row][col].Breed();// call the breed function which return an object of its class
            grid[row][col+1].hasMovedorBreed= true; // set the hasMovedorBreed to true for the new object
        }
        else if((!(row==9) && ! (grid[row+1][col] instanceof Creature))){
            grid[row+1][col]= grid[row][col].Breed(); // call the breed function which return an object of its class
            grid[row+1][col].hasMovedorBreed= true; // set the hasMovedorBreed to true for the new object
        }
        else if((!(col==0) && ! (grid[row][col-1] instanceof Creature))){
            grid[row][col-1]= grid[row][col].Breed(); // call the breed function which return an object of its class
            grid[row][col-1].hasMovedorBreed= true;
        }
    }

}