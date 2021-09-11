package com.skilldistillery.film.dao;

import java.sql.Connection;

import com.skilldistillery.film.entities.Film;


public class FilmDaoJdbcImpl implements FilmDAO {

	@Override
	public Film findById(int filmId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Film createFilm(Film film) {
		 void runSQL(); {
			    String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
			    String user = "student";
			    String pword = "student";
			   
		    try {
		    	 Connection conn = null;
		    	 String sql = "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features)" 
		    	 + " VALUES(?,?,?,?,?,?,?,?,?,?)";
		    	
		    }
		    	 
		   
		    }
	}

}
