public class Other4 {

	public static void other4() {
		int height = -1;

		Scanner in = new Scanner(System.in);
		
		while (height < 0 || height > 23 {
			height = in.nextInt();

			String textToOutput = "Enter height: ";
			System.out.println(textToOutput);
		}
		
		in.close();

		int row = 0;
		while (row < height) {
			int column = 0;

			while (column < height - row - 1) {
				System.out.print("-");
				column++;
			}

			while (column < height) {
				System.out.print(row % 10);
				column++;
			}
			System.out.println();

			row++;
		}
	}
}
