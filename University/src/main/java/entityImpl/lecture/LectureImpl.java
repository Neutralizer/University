package entityImpl.lecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityAPI.student.Student;
import entityImpl.professor.ProfessorImpl;
import entityImpl.student.StudentImpl;

@Entity
@Table(name = "lecture")
public class LectureImpl implements Lecture, Serializable {

	@Id
	@Column(name="lecture_id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	int id;
	String name;
	@OneToOne (cascade = {CascadeType.ALL},targetEntity = ProfessorImpl.class)
	Professor leadingProfessor;
	@ManyToMany (mappedBy="attendedLectures",targetEntity = StudentImpl.class)
	Collection<Student> attendingStudents = new ArrayList<Student>();

	public LectureImpl() {
	}

	public LectureImpl(String name) {
		this.name = name;
	}

	public LectureImpl(int id, String name, Professor professor) {
		this.id = id;
		this.name = name;
		this.leadingProfessor = professor;
	}

	public LectureImpl(int id, String name, Professor leadingProfessor,
			Collection<Student> attendingStudents) {
		this.id = id;
		this.name = name;
		this.attendingStudents = attendingStudents;
		this.leadingProfessor = leadingProfessor;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Professor getLeadingProfessor() {
		return leadingProfessor;
	}

	public Collection<Student> getAttendingStudents() {
		return this.attendingStudents;
	}

}
