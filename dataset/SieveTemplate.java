import java.util.LinkedList;
 
public class Sieve{
       public static LinkedList<Integer> sieve(int X){
               if(X < 2) return new LinkedList<Integer>();
               LinkedList<Integer> primes = new LinkedList<Integer>();
               LinkedList<Integer> nums = new LinkedList<Integer>();
 
               for(int Y = 2;Y <= X;Y++){ //unoptimized
                       nums.add(Y);
               }
 
               while(nums.size() > 0){
                       int nextPrime = nums.remove();
                       for(int Y = nextPrime * nextPrime;Y <= X;Y += nextPrime){
                               nums.removeFirstOccurrence(Y);
                       }
                       primes.add(nextPrime);
               }
               return primes;
       }
}