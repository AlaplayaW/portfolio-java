package com.perrine.portfolio.model;


import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "T_PORTFOLIO")
public class Portfolio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String title;

	private String customer;

	@Column(length = 2048)
	private String description;

	@ManyToMany
	@JoinTable(name = "T_PORTFOLIO_TAGS", joinColumns = @JoinColumn(name = "T_PORTFOLIO_ID") , inverseJoinColumns = @JoinColumn(name = "TAGS_ID") )
	private List<Tag> tags;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}


	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, customer, description, tags);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Portfolio portfolio = (Portfolio) o;
		return Objects.equals(id, portfolio.id) &&
				Objects.equals(title, portfolio.title) &&
				Objects.equals(customer, portfolio.customer) &&
				Objects.equals(description, portfolio.description) &&
				Objects.equals(tags, portfolio.tags);
	}
}
