package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer taskId;
	
	private String taskType;
	
	private String taskName;
	
	private String description;
	
	private String assignee;
	
	private String status;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Sprint sprint;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private SprintTask sprintTask;
}
