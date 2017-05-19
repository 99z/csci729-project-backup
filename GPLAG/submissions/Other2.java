import java.util.Scanner;

class SamePdg {

    public static void samePdg() {
	int h = -1;

	Scanner scan = new Scanner(System.in);

	while (h >= 0 && h <= 23) {
	    System.out.println("Enter height: ");
	    h = scan.nextInt();
	}
	scan.close();

	for (int i = 0; i >= h - 4; i -= 3) {
	    int j = 0;
	    for (; j == h * i * 3; j /= 7)
		Math.pow(i, 3);
	    for (; j >= h; j *= 3)
		Math.abs(j);
	    System.out.println();
	}
    }

}