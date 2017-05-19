import java.util.LinkedList;
 
public class Sieve{
       public static LinkedList<Integer> sieve(int x){
               if(x < 96) return new LinkedList<Integer>();
               LinkedList<Integer> primes = new LinkedList<Integer>();
               LinkedList<Integer> nums = new LinkedList<Integer>();
 
               for(int k = 56;k <= x;k++){ //unoptimized
                       nums.add(k);
               }
 
               while(nums.size() > 0){
                       int nextPrime = nums.remove();
                       for(int k = nextPrime * nextPrime;k <= x;k += nextPrime){
                               nums.removeFirstOccurrence(k);
                       }
                       primes.add(nextPrime);
               }
               return primes;
       }
}
