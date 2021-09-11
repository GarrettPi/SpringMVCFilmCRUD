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

	@RequestMapping(path = "addFilm.do", method = RequestMethod.POST)
	public ModelAndView addFilm(Film film, RedirectAttributes redir) {
		ModelAndView mv = new ModelAndView();
		Film newFilm = filmDao.createFilm(film);
		redir.addFlashAttribute("film", newFilm);
		mv.setViewName("redirect:filmAdded.do");
		return mv;
	}

	@RequestMapping(path = "filmAdded.do", method = RequestMethod.GET)
	public ModelAndView created() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("filmadded");
		return mv;
	}

	@RequestMapping(path = "idLookup.do", method = RequestMethod.POST)
	public ModelAndView idLookup(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		ModelAndView mv = new ModelAndView();
		if (film != null)
			mv.addObject(film);
		mv.setViewName("viewfilm");
		return mv;
	}

	@RequestMapping(path = "filmToUpdate.do")
	public ModelAndView filmToUpdate(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		ModelAndView mv = new ModelAndView();
		if(film!=null) mv.addObject(film);
		mv.setViewName("updatefilm");
		return mv;
	}
	
	@RequestMapping(path="updateFilm.do")
	public ModelAndView updateFilm(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		ModelAndView mv = new ModelAndView();
		
		return mv;
	}

	@RequestMapping(path = "deleteFilm.do")
	public ModelAndView deleteFilm(int filmId) {
		ModelAndView mv = new ModelAndView();
		Film film = null;
		film = filmDao.findFilmById(filmId);
		if (film != null) {
			filmDao.deleteFilm(film);
			mv.addObject(film);
		}
		mv.setViewName("filmdeleted");
		return mv;
	}

	@RequestMapping(path = "keywordLookup.do")
	public ModelAndView keywordLookup(String keyword) {
		ModelAndView mv = new ModelAndView();

		return mv;
	}
}
