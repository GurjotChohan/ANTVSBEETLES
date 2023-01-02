

public class Ant extends Creature {
    

    public  int Move(String distance)
    {
        char[] chars;
        String highlowvaluestr;
        
        if(!(distance.equals("0000")))
        {
            chars = distance.toCharArray();
            highlowvaluestr= findlowandhigh(chars);
            if(isrepated(chars, (int)(highlowvaluestr.charAt(0))))
                if(!(distance.indexOf('0')==-1))
                {
                    return distance.indexOf('0');
                }
                else
                    return distance.indexOf(highlowvaluestr.charAt(1));
            else
                {
                    if(distance.indexOf(highlowvaluestr.charAt(0))== 0 || distance.indexOf(highlowvaluestr.charAt(0))==1)
                        return ((distance.indexOf(highlowvaluestr.charAt(0)))+2); 
                    else 
                        return ((distance.indexOf(highlowvaluestr.charAt(0)))-2);
                }    
        }
        else 
            return -1;
    }
    public  Ant Breed()
    {
        return new Ant(); 
    }

    private String findlowandhigh(char[] chars)
    {

        int lowest= 9;
        int highest= 0; 

        for(int i=0; i<4; i++)
        {
            if(!(Character.getNumericValue(chars[i])==0))
                if((Character.getNumericValue(chars[i])<lowest)) 
                    lowest= Character.getNumericValue(chars[i]);
                if((Character.getNumericValue(chars[i])>highest))
                    highest= Character.getNumericValue(chars[i]);
        }
        if (lowest==0)
            return ( ""  + highest); 
        else 
            return ( "" + lowest + highest); 
    }

    private boolean isrepated(char[] chars, int value)
    {
        int j=0; 
        for(int i=0; i<4; i++)
        {
            if((int)chars[i] == value )
                if (j>=1)
                    return true;
                else
                    j++; 
        }
        return false; 

    }
}