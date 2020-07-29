package com.perrine.portfolio.controller;

import com.perrine.portfolio.model.Portfolio;
import com.perrine.portfolio.model.Tag;
import com.perrine.portfolio.repository.PortfolioRepository;
import com.perrine.portfolio.repository.TagRepository;
import com.perrine.portfolio.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@Controller
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepo;

    @Autowired
    private TagRepository tagRepo;

//	@Autowired
//	private SCFileService fileService;

    @Autowired
    private PortfolioService portfolioService;

    @Transactional
    @ModelAttribute("allTags")
    public List<String> allTagsName() {
        return tagRepo.findAllTagNames();
    }

    @Transactional
    @RequestMapping(value = "/admin/portfolio", method = RequestMethod.GET)
    public String portfoliosAdmin(Model model, @ModelAttribute Tag tag) {
        model.addAttribute("portfolio", new Portfolio());
        model.addAttribute("allPortfolios", portfolioRepo.findAll());
        model.addAttribute("tags", tagRepo.findAll());

        return "admin/portfolioAdmin";
    }

    @RequestMapping(value = "/admin/portfolio", method = RequestMethod.POST)
    public String savePortfolio(@RequestParam(value = "tagsStringArray", required = false) String[] tags,
                                @Valid Portfolio portfolio, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "admin/portfolioAdmin";
        }

        portfolioService.saveOrUpdate(portfolio, tags);

        model.asMap().clear();
        model.addAttribute("mensagem",
                portfolio.getId() == null ? "Portfolio added successfully!" : "Portfolio updated successfully!");

        return "redirect:/admin/portfolioAdmin";
    }


    @RequestMapping(value = "/admin/portfolio/{id}")
    public String singlePortfolio(@PathVariable(value = "id") Integer id, Model model) {
        Portfolio portfolio =
                portfolioRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("id"));

        if (portfolio == null) {
            portfolio = new Portfolio();
        }

        model.addAttribute("portfolio", portfolio);

        return "/admin/portfolioAdmin";
    }

//    @RequestMapping(value = "/admin/portfolio/{id}/upload", method = RequestMethod.POST)
//    public @ResponseBody
//    UploadResponse portfolioFileUpload(@PathVariable("id") Integer idPortfolio, Model model,
//                                       @RequestParam("file") MultipartFile portfolioFile, HttpServletResponse response) {
////
////        SCFile savedFile;
////        try {
////            savedFile = fileService.uploadPortfolioFile(idPortfolio, portfolioFile);
////        } catch (IOException e) {
////            return null;
////        }
//
//        return new UploadResponse(idPortfolio, savedFile.getId());
//    }

//    @RequestMapping(value = "/admin/portfolio/image/{portfolioId}/{fileId}", method = RequestMethod.DELETE)
//    public @ResponseBody
//    String portfolioDeleteImage(@PathVariable("portfolioId") Integer portfolioId,
//                                @PathVariable("fileId") Integer fileId, HttpServletResponse response) {
//
//        fileService.deletePortfolioFile(portfolioId, fileId);
//
//        return "{message: File removed sucessfully}";
//    }

//    @RequestMapping(value = "/admin/portfolio/image/setcover/{portfolioId}/{fileId}", method = RequestMethod.PUT)
//    public @ResponseBody
//    String portfolioSetComverImage(@PathVariable("portfolioId") Integer portfolioId,
//                                   @PathVariable("fileId") Integer fileId, HttpServletResponse response) {
//
//        portfolioRepo.setCoverImage(portfolioId, fileId);
//
//        return "{message: File removed sucessfully}";
//    }

    @RequestMapping(value = "/admin/portfolio/tag/check/{tagName}", method = RequestMethod.POST)
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