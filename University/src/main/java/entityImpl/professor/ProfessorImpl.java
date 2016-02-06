package entityImpl.professor;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Target;

import entityAPI.Lecture.Lecture;
import entityAPI.professor.Professor;
import entityImpl.lecture.LectureImpl;

@Entity
@Table(name = "professor")
public class ProfessorImpl implements Professor, Serializable {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	int id;
	String name;

	public ProfessorImpl() {
	}
	
	public ProfessorImpl(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public ProfessorImpl(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}


}
