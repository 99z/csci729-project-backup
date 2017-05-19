public class Reference {
	public static void reference() {
		int h = -1;

		Scanner scan = new Scanner(System.in);

		while (h < 0 || h > 23) {
			System.out.println("Enter height: ");
			h = scan.nextInt();
		}

		scan.close();

		for (int i = 0; i < h; i += 1) {
			int j = 0;
			for (; j < h - i - 1; j += 1)
				System.out.print("-");
			for (; j < h; j += 1)
				System.out.print(j % 10);
			System.out.println();
		}
	}
}
