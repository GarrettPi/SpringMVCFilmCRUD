package com.skilldistillery.film.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.skilldistillery.film.dao.FilmDAO;
import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
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
	
	@RequestMapping(path="addActor.do", method = RequestMethod.POST)
	public ModelAndView addActor(Actor actor, RedirectAttributes redir) {
		ModelAndView mv = new ModelAndView();
		Actor newActor = filmDao.addActor(actor);
		redir.addFlashAttribute("actor", newActor);
		mv.setViewName("redirect:actorAdded.do");
		return mv;
	}
	
	@RequestMapping(path="actorAdded.do", method = RequestMethod.GET)
	public ModelAndView actorAdded() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("actoradded");
		return mv;
	}

	@RequestMapping(path = "idLookup.do", method = RequestMethod.POST)
	public ModelAndView idLookup(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		List<Actor> actors = filmDao.findActorsByFilmId(filmId);
		List<Category> categories = filmDao.findCategoriesByFilmId(filmId);
		ModelAndView mv = new ModelAndView();
		if (film != null) {
			mv.addObject(film);
			mv.addObject("actors", actors);
			mv.addObject("categories", categories);
		}
		mv.setViewName("viewfilm");
		return mv;
	}

	@RequestMapping(path = "filmToUpdate.do")
	public ModelAndView filmToUpdate(int filmId) {
		Film film = filmDao.findFilmById(filmId);
		ModelAndView mv = new ModelAndView();
		if (film != null)
			mv.addObject(film);
		mv.setViewName("updatefilm");
		return mv;
	}

	@RequestMapping(path = "updateFilm.do", method = RequestMethod.POST)
	public ModelAndView updateFilm(@RequestParam("filmId") int filmID, Film film) {
		ModelAndView mv = new ModelAndView();
		System.out.println(film);
		film.setId(filmID);
		System.out.println(film.getId());
		Film newFilm = filmDao.updateFilm(film);
		mv.setViewName("filmupdated");
		if (newFilm == null) {
			newFilm = new Film();
			newFilm.setId(-1);
		}
		mv.addObject("film", newFilm);
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
		List<Film> films = filmDao.findFilmByKeyword(keyword);
		ModelAndView mv = new ModelAndView();
		mv.setViewName("keywordfilms");
		mv.addObject("films", films);
		return mv;
	}
}
