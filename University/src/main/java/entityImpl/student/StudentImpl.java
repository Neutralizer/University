package entityImpl.student;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import entityAPI.Lecture.Lecture;
import entityAPI.student.Student;
import entityImpl.lecture.LectureImpl;

@Entity
@Table(name = "student")
public class StudentImpl implements Student, Serializable {

	@Id
	@Column(name="student_id")
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	int id;
	String name;
	@ManyToMany 
	(cascade = {CascadeType.ALL}, targetEntity = LectureImpl.class)
	@JoinTable(name="student_lecture", 
			joinColumns={@JoinColumn(name="student_id")}, 
			inverseJoinColumns={@JoinColumn(name="lecture_id")})
	Collection<Lecture> attendedLectures = new ArrayList<Lecture>();

	public StudentImpl() {
	}

	public StudentImpl(String name) {
		this.name = name;
	}

	public StudentImpl(int id, String name, Collection<Lecture> attendedLectures) {
		this.id = id;
		this.name = name;
		this.attendedLectures = attendedLectures;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Collection<Lecture> getAttendedLectures() {
		return attendedLectures;
	}

}
