package com.skilldistillery.film.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skilldistillery.film.dao.FilmDAO;
import com.skilldistillery.film.entities.Film;

@Controller
public class FilmController {
	@Autowired
	private FilmDAO filmDao;

	@RequestMapping(path = { "/", "home.do" })
	public String home() {                   
		return "home";
	}
	
	@RequestMapping(path="addFilm.do", method=RequestMethod.POST)
	public ModelAndView addFilm(Film film, RedirectAttributes redir) {
		ModelAndView mv = new ModelAndView();
		filmDao.createFilm(film);
		mv.setViewName("redirect:filmAdded.do");
		redir.addFlashAttribute("film", film);
		return mv;
	}
	
	@RequestMapping(path="filmAdded.do", method=RequestMethod.GET)
	public ModelAndView created() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("filmadded");
		return mv;
	}
	
	@RequestMapping(path="idLookup.do", method=RequestMethod.POST)
	public ModelAndView idLookup(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		ModelAndView mv = new ModelAndView();
		if(film != null) mv.addObject(film);
		mv.setViewName("viewfilm");
		return mv;
	}
}
