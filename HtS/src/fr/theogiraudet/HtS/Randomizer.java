package fr.theogiraudet.HtS;


import java.util.Random;

public class Randomizer{
    
    private static Random random = new Random();
    private static boolean state = false;
    private static int coords[] = new int[3];
    
    public static int Rand(int min, int max){    
        return random.nextInt(max+1-min)+min;
    }
    
    public static boolean RandRate(int rate){
        if(random.nextInt(100) < rate){
            state = true;
        }
        else
        {
            state = false;
        }    
        return state;
    }
    
    public static  int[] RandCoord(int mx, int Mx,int mz,int Mz,int my,int My){
        coords[0] = Rand(mx, Mx);
        coords[1] = Rand(mz, Mz);
        coords[2] = Rand(my, My);
        return coords;
    }    
}