package com.masai.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UserCurrentSession {
	@Id
	@Column(unique = true)
	private Integer userId;
	private String uniqueId;
	private LocalDateTime localDateTime;
}


