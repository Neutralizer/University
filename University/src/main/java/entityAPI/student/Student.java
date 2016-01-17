package entityAPI.student;

import java.util.Collection;

import entityAPI.Lecture.Lecture;

public interface Student {

	int getId();
	
	String getName();
	
	Collection<Lecture> getAttendedLectures();
	
	
}
