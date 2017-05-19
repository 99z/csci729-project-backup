/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problems;

import java.util.Scanner;

/**
 *
 * @author Vaigunth
 */
public class Main{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){
        // TODO code application logic here
        try 
        {
            Scanner sc = new Scanner(System.in);
            long numTC = Long.parseLong(sc.next());
            for (long i = 0; i < numTC; i++) {
                long value = Long.parseLong(sc.next()), counter = value/2048;
                value %= 2048;
                System.out.println((Long.bitCount(value)+counter));
            }
        }
        catch(Exception e){

        }
    }

}

