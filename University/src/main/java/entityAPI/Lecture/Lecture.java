package entityAPI.Lecture;

import java.util.Collection;

import entityAPI.professor.Professor;
import entityAPI.student.Student;

public interface Lecture {

	int getId();

	String getName();

	Professor getLeadingProfessor();
	
	Collection<Student> getAttendingStudents();

}
