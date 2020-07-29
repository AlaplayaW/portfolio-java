package com.perrine.portfolio.controller;

import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PublicController {

	@Autowired
	private PortfolioRepository portfolioRepo;


	@RequestMapping("/home")
	public String index(Model model) {
		List<Portfolio> result = portfolioRepo.findAll();
		int size = result.size();
		if (size != 0) {
			int numRows = (size % 3 == 0 && size > 3) ? (size / 3) : (size / 3) + 1;
			List<List<Portfolio>> portfolios = new ArrayList<List<Portfolio>>();
			for (int i = 0; i < numRows; i++) {
				List<Portfolio> row = new ArrayList<Portfolio>();
				int numColumns = 3;

				// last row
				if (i == numRows - 1) {
					numColumns = size % 3 == 0 ? 3 : size % 3;
				}

				for (int j = 0; j < numColumns; j++) {
					row.add(result.get(i == 0 ? j : (i * 3) + j));
				}
				portfolios.add(row);
			}

			model.addAttribute("allPortfolios", portfolios);
		}

		return "index";
	}

	@Transactional
	@RequestMapping("/portfolio")
	public String portfolios(Model model) {
		model.addAttribute("allPortfolios", portfolioRepo.findAll());

		return "portfolio";
	}

	@Transactional
	@RequestMapping("/portfolio/{portfolioId}")
	public String portfolioDetail(@PathVariable("portfolioId") Integer portfolioId, Model model) {
		model.addAttribute("portfolio", portfolioRepo.findById(portfolioId));
		model.addAttribute("allPortfolios", portfolioRepo.findAll());

		return "portfolio";
	}

//	@RequestMapping(value = "/file/{fileId}", method = RequestMethod.GET)
//	public void getFile(@PathVariable("fileId") Integer fileId, @Param("thumb") boolean thumb, HttpServletResponse response) {
//		try {
//			fileService.getPortfolioFile(fileId, thumb, response);
//		} catch (IOException e) {
//			// TODO handle excetion
//			e.printStackTrace();
//		}
//	}

//	@RequestMapping(value = "/mail/send", method = RequestMethod.POST)
//	public String sendEmail(@Valid Mail email) {
//		SimpleMailMessage mailMessage = new SimpleMailMessage();
//
//		mailMessage.setTo(FROM);
//		mailMessage.setReplyTo(email.getEmail());
//		mailMessage.setFrom(FROM);
//		mailMessage.setSubject("Super Cloud Website");
//		mailMessage.setText(email.getMessage());
//
//		javaMailSender.send(mailMessage);
//
//		return "index";
//	}
}
