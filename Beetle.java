

public class Beetle extends Creature {

    private int turnssinceeat=0;

    /**************************************************************************************************************************************
     * Function name and acceser type:(name: Move acceser type: public)
     * return type: int (-1= dont move, 0= move north, 1= move east, 2 = move south, 3= move west)
     * Function parameter: String distance (8 letter string with index 0 represting the distance of an ant north if 0 then no ant) (4-7) repsents the num of ajacent ants
     * Function purpose: this function takes the string from the main and goes through it to find the closet ant, if there is a tie then the function check the last four letters 
     *  of the string for the higest ajacent ants and return the direction with the most ants. if no ants in any direction then return -1
     ***************************************************************************************************************************************/
    public  int Move(String distance)
    {

        char[] chars;
        String nearestant; 
        int highestindex; 
        int highestvalue; 

        // if distance string is 00000000 meaning there are no ants in any four directions
        if(!(distance.equals("00000000")))
        {
            chars = distance.toCharArray(); // turn the string an chars array
            nearestant= findclosestant(chars); // finds the index of closest ants

            if(nearestant.length()>1) // if there are mutiple nearest ant
            {
                highestvalue=Character.getNumericValue(chars[((int)nearestant.charAt(0)-'0')+4]); // assign the intial highest value for ajacent ants from the nearestant
                highestindex=((int)nearestant.charAt(0)-'0'); // assign the intial index using the nearestant string

                for(int i=0; i<nearestant.length(); i++) // create a loop to go through the all the ties
                {
                    if(Character.getNumericValue(chars[((int)nearestant.charAt(i)-'0')+4])>highestvalue) // get numerical value of the number of ajacent ant for the next tie and comapre to the highest value
                        {highestvalue=Character.getNumericValue(chars[((int)nearestant.charAt(i)-'0')+4]); // if the numofajacent ants is higher update the highest
                        highestindex=((int)nearestant.charAt(i)-'0');}// update the highest index
                }
                return highestindex; // return the highestindex value found thorough ajajents ants part of the string
            }
            else 
                return (nearestant.charAt(0)-'0');  //return nearestant (contains the index of the closet ant)
        }
        else
            return -1; // return -1 if no ant
    } 

     /**************************************************************************************************************************************
     * Function name and acceser type:(name: Breed acceser type: public)
     * return type: Beetle
     * Function parameter: none
     * Function purpose: return an instance of the class
     ***************************************************************************************************************************************/

    public Beetle Breed()
    {
        return (new Beetle()); // return a new instance of the beetle class
    } 

    /**************************************************************************************************************************************
     * Function name and acceser type:(name: findclosestant acceser type: Private)
     * return type: string that has the index of the nearest ants (mutiple if tie)
     * Function parameter: chars array
     * Function purpose: find the index of the cloest ant to the beetle using the first 4 letters of the disatnce string return mutiplr char string
     * if there is a tie
     ***************************************************************************************************************************************/

    private String findclosestant(char[] chars)
    {
 
        int lowestvalue=9; // assign the highest possible as intial
        String s="";  // create and intilize a string

        for(int i=0; i<4; i++) // go through the first four charcters of chars array
        {
            if(!(Character.getNumericValue(chars[i])==0)) // make sure the value is not 0 bc 0= no ant
                if((Character.getNumericValue(chars[i])<=lowestvalue)) // find the lowest distance
                    lowestvalue= Character.getNumericValue(chars[i]);
        }

        for(int i=0; i<4; i++)
        {
            if(Character.getNumericValue(chars[i])==lowestvalue) // check if the nearest ant disatnce is repated
                s=""+s+i; // if so update the s string
        }

            return (s); // return the index of nearest ants (index is realted to the direction)

    }

     /**************************************************************************************************************************************
     * Function name and acceser type:(name: Starve acceser type: public)
     * return type: void
     * Function parameter: boolean antpresent
     * Function purpose: update the turnssinceeat varaible if there is an antpresent to 0 else increment by 1
     ***************************************************************************************************************************************/

    public void Starve(boolean antpresent)
    {
        if(antpresent==true)
            turnssinceeat = 0; 
        else
            turnssinceeat++; 
    }

    /**************************************************************************************************************************************
     * Function name and acceser type:(name: returnturnssinceeat acceser type: public)
     * return type: int 
     * Function parameter: None
     * Function purpose: acessor for returnturnssinceeat
     ***************************************************************************************************************************************/

    public int returnturnssinceeat() { return turnssinceeat; }

}

