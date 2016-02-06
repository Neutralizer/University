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

	public StudentImpl() {
	}
	
	public StudentImpl(String name) {
		this.name = name;
	}
	
	public StudentImpl(int id, String name) {
		this.id = id;
		this.name = name;
	}

	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}


}
