public class FizzBuzz{
	public static void main(String[] args){
		for(int X = 1; X <= 100; X++){
			if(X % 15 == 0){
				System.out.println("FizzBuzz");
			}else if(X % 3 == 0){
				System.out.println("Fizz");
			}else if(X % 5 == 0){
				System.out.println("Buzz");
			}else{
				System.out.println(X);
			}
		}
	}
}