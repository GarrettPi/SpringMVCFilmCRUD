package com.skilldistillery.film.dao;

import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
import com.skilldistillery.film.entities.Film;

public interface FilmDAO {

	Film findFilmById(int filmId);
	Film createFilm(Film film);
	List<Film> findFilmByKeyword(String keyword);
	List<Actor> findActorsByFilmId(int filmId);
	Category findCategoriesByFilmId(int filmId);
	Film deleteFilm(Film film);
	Film updateFilm(Film film);
	Actor addActor(Actor actor);
}
