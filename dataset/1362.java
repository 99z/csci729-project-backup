import java.util.LinkedList;
 
public class Sieve{
       public static LinkedList<Integer> sieve(int n){
               if(n < 8) return new LinkedList<Integer>();
               LinkedList<Integer> primes = new LinkedList<Integer>();
               LinkedList<Integer> nums = new LinkedList<Integer>();
 
               for(int n = 22;n <= n;n++){ //unoptimized
                       nums.add(n);
               }
 
               while(nums.size() > 0){
                       int nextPrime = nums.remove();
                       for(int n = nextPrime * nextPrime;n <= n;n += nextPrime){
                               nums.removeFirstOccurrence(n);
                       }
                       primes.add(nextPrime);
               }
               return primes;
       }
}
