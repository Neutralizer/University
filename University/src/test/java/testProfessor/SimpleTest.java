package testProfessor;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class SimpleTest {

	@Test
	public void test() {
		ArrayList<Integer> listLocalNumbers = new ArrayList<Integer>();

		ArrayList<Integer> listDBNumbers = new ArrayList<Integer>();

		listLocalNumbers.add(3);
		listLocalNumbers.add(2);
		listLocalNumbers.add(1);

		listDBNumbers.add(2);
		listDBNumbers.add(1);
		int counter = 0;
		int number = 0;

		if (!listDBNumbers.equals(listLocalNumbers)) {
			System.out.println("they aren't equal");

			if (listDBNumbers.size() > listLocalNumbers.size()) {

				// removing a lecture

				while (counter < listLocalNumbers.size()) {
					if (!listLocalNumbers.contains(listDBNumbers.get(counter))) {
						number = listDBNumbers.get(counter);
					}

					counter++;
				}
				if (number == 0) {
					number = listDBNumbers.get(listDBNumbers.size() - 1);
				}
				System.out.println(number + " number");

			} else { 
				// adding a lecture

				while (counter < listDBNumbers.size()) {
					if (!listDBNumbers.contains(listLocalNumbers.get(counter))) {
						number = listLocalNumbers.get(counter);
					}

					counter++;
				}
				if (number == 0) {
					number = listLocalNumbers.get(listLocalNumbers.size() - 1);
				}
				System.out.println(number + " number");
			}

		}

	}

}
