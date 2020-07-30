package com.perrine.portfolio.service;

import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.repository.PortfolioRepository;
import com.perrine.portfolio.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;


@Service
public class PortfolioService {

	@Autowired
	private PortfolioRepository portfolioRepo;

	@Autowired
	private TagRepository tagRepo;


	public void saveOrUpdate(Portfolio portfolio, String[] tags) {
		if (tags != null) {
			for (int i = 0; i < tags.length; i++) {
				tags[i] = StringUtils.capitalizeWords(tags[i].toLowerCase());
			}

			portfolio.setTags(tagRepo.findAllByNameAllIgnoreCaseIn(tags));
		}

		portfolioRepo.save(portfolio);
	}

}
