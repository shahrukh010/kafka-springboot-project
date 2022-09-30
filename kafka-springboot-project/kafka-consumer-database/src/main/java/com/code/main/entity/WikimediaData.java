package com.code.main.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "wikimedia_recentchange")
public class WikimediaData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Lob
	private String wikimediaData;


	public WikimediaData() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWikimediaData() {
		return wikimediaData;
	}

	public void setWikimediaData(String wikimediaData) {
		this.wikimediaData = wikimediaData;
	}

}
