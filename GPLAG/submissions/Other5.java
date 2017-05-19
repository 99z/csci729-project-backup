public class Other5 {

	public static void other5() {
		  for (int i = 1; i < n; i++) {
			   for (int a = 0; a < i; a++) {
			    if (A[i] > A[a] && lis[i] < lis[a] + 1) {
			     lis[i] = lis[a] + 1;
			    }
			   }
			  }

			  // Pick maximum of all LIS values
			  for (int i = 0; i < n; i++)
			   if (max < lis[i])
			    max = lis[i];
			  return max;
			 }

			 public static void main(String[] args) {

			  long startTime = System.currentTimeMillis();

			  Scanner scan = new Scanner(System.in);
			  String data = scan.nextLine();

			  int n = Integer.parseInt(data);

			  data = scan.nextLine();
			  String[] tmpDataArray = data.split(" ");
			  int[] dataArray = new int[tmpDataArray.length];

			  for (int i = 0; i < dataArray.length; ++i) {
			   dataArray[i] = Integer.parseInt(tmpDataArray[i]);
			  }

			  int j = dataArray.length;
			  int maxLen = incrSubseqDP(j, dataArray);

			  System.out.println(maxLen);
			  long endTime = System.currentTimeMillis();
			  long totalTime = endTime - startTime;
			  System.out.println(totalTime);
			  scan.close();
	}
}
