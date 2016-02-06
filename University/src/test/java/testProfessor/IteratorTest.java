package testProfessor;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Test;

import entityAPI.student.Student;

public class IteratorTest {

	@Test
	public void test() {
		
		ArrayList<Integer> listNumbers = new ArrayList<Integer>();
		listNumbers.add(1);
		listNumbers.add(2);
	
		Collection<Integer> students = new ArrayList<Integer>();

		// iterator
		Iterator<Integer> iter = listNumbers.iterator();
		while (iter.hasNext()) {
			students.add((iter.next()));
		}
		System.out.println(students.size());
		
	}

}
