package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class SprintTask {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer sprintTaskId;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Task> taskList=new ArrayList<>();
}
