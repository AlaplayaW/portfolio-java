package com.perrine.portfolio.controller;

import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.model.Tag;
import com.perrine.portfolio.repository.PortfolioRepository;
import com.perrine.portfolio.repository.TagRepository;
import com.perrine.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private TagRepository tagRepo;

    @Autowired
    private PortfolioService portfolioService;

//    @ModelAttribute("allTags")
//    public List<String> allTagsName() {
//        return tagRepo.findAllTagNames();
//    }

    @GetMapping("/admin/portfolio")
    public String portfoliosAdmin(Model model) {
        model.addAttribute("portfolio", new Portfolio());
        model.addAttribute("allPortfolios", portfolioRepo.findAll());
//        model.addAttribute("allTags", tagRepo.findByName();


        return "admin/portfolio";
    }

    @PostMapping("/admin/portfolio")
    public String savePortfolio(@RequestParam(value = "tagsStringArray", required = false) String[] tags,
                                @Valid Portfolio portfolio, Model model) {

        portfolioService.saveOrUpdate(portfolio, tags);

        model.asMap().clear();

        return "redirect:/admin/portfolio";
    }

    @GetMapping("/admin/portfolio/{id}")
    public String singlePortfolio(@PathVariable(value = "id") Integer id, Model model) {
        Optional<Portfolio> optionalPortfolio = portfolioRepo.findById(id);

        Portfolio portfolio = new Portfolio();
        if (optionalPortfolio.isPresent()) {
            portfolio = optionalPortfolio.get();
        }

        model.addAttribute("portfolio", portfolio);

        return "admin/portfolio";
    }

    @PostMapping("/admin/portfolio/tag/check/{tagName}")
    public @ResponseBody
    String checkTag(@PathVariable("tagName") String tagName) {
        Tag tag = tagRepo.findByName(tagName);

        if (tag == null) {
            tag = new Tag(StringUtils.capitalizeWords(tagName.toLowerCase()));

            tagRepo.save(tag);

            return "{message:'Tag added sucesfully'}";
        }

        return "{}";
    }

}