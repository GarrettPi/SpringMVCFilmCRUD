package com.skilldistillery.film.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.film.entities.Actor;
import com.skilldistillery.film.entities.Category;
import com.skilldistillery.film.entities.Film;
import com.skilldistillery.film.entities.InventoryItem;

public class FilmDaoJdbcImpl implements FilmDAO {
	String url = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	String user = "student";
	String pword = "student";

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		Film film = null;
		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, rental_rate, "
				+ "length, replacement_cost, rating, special_features FROM film where id = ?";

		try (Connection conn = DriverManager.getConnection(url, user, pword);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, filmId);
			try (ResultSet rs = ps.executeQuery();) {
				if (rs.next()) {
					film = new Film(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
							rs.getInt(6), rs.getDouble(7), rs.getInt(8), rs.getDouble(9), rs.getString(10),
							rs.getString(11));
				}
			} catch (SQLException e) {
				System.err.println("Database error: " + e);
			}
		} catch (SQLException e) {
			System.err.println("Database Error: " + e);
		}
		return film;
	}

	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		keyword = keyword.trim();
		keyword = "%" + keyword + "%";
		String sql = "SELECT id, title, description, release_year, language_id, rental_duration, "
				+ "rental_rate, length, replacement_cost, rating, special_features FROM film WHERE (title LIKE ? OR description LIKE ?)";
		try (Connection conn = DriverManager.getConnection(url, user, pword);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setString(1, keyword);
			ps.setString(2, keyword);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					films.add(new Film(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
							rs.getInt(6), rs.getDouble(7), rs.getInt(8), rs.getDouble(9), rs.getString(10),
							rs.getString(11)));
				}
			} catch (SQLException e) {
				System.err.println("Database Error: " + e);
			}
		} catch (SQLException e) {
			System.err.println("Database Error: " + e);

		}
		return films;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> actors = new ArrayList<>();
		String sql = "SELECT a.id, a.first_name, a.last_name FROM actor a JOIN film_actor f on a.id = f.actor_id where f.film_id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, pword);
				PreparedStatement ps = conn.prepareStatement(sql);) {
			ps.setInt(1, filmId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					actors.add(new Actor(rs.getInt(1), rs.getString(2), rs.getString(3)));
				}
			} catch (SQLException e) {
				System.err.println("Database Error " + e);
			}
		} catch (SQLException e) {
			System.err.println("Database Error: " + e);
		}
		return actors;
	}

	@Override
	public List<Category> findCategoriesByFilmId(int filmId) {
		List<Category> categories = new ArrayList<>();
		String sql = "SELECT id, name FROM category c JOIN film_category f ON c.id = f.category_id WHERE f.film_id = ?";
		try (Connection conn = DriverManager.getConnection(url, user, pword);
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, filmId);
			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					categories.add(new Category(rs.getInt(1), rs.getString(2)));
				}
			} catch (SQLException e) {
				System.err.println("Database Error: " + e);
			}
		} catch (SQLException e) {
			System.err.println("Database Error: " + e);

		}
		return categories;
	}

	@Override
	public Film createFilm(Film film) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false); // Start transaction
			String sql = "INSERT INTO film (title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features)"
					+ " VALUES(?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			System.out.println(film.getReplacementCost());
			st.setString(1, film.getTitle());
			st.setString(2, film.getDescription());
			st.setInt(3, film.getReleaseYear());
			st.setInt(4, film.getLanguageId());
			st.setInt(5, film.getRentalDuration());
			st.setDouble(6, film.getRentalRate());
			st.setInt(7, film.getLength());
			st.setDouble(8, film.getReplacementCost());
			st.setString(9, film.getRating());
			st.setString(10, film.getSpecialFeatures());

			try {
				int cf = st.executeUpdate();
				conn.commit();
				System.out.println(cf + "film records created");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("New film ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during inserts.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Film deleteFilm(Film film) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false); // Start transaction
			String sql = "DELETE FROM film WHERE film.id=?";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, film.getId());

			try {
				int df = st.executeUpdate();
				conn.commit();
				System.out.println(df + "film records deleted");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Delete film ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during delete");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;

	}

	@Override
	public Film updateFilm(Film film) {
		Connection conn = null;

		String sql = "UPDATE film SET title = ?,description = ?,release_year = ?,language_id = ?,rental_duration = ?,rental_rate = ?,length = ?,replacement_cost = ?,rating = ?,special_features = ? WHERE id = ?";
		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, film.getTitle());
			st.setString(2, film.getDescription());
			st.setInt(3, film.getReleaseYear());
			st.setInt(4, film.getLanguageId());
			st.setInt(5, film.getRentalDuration());
			st.setDouble(6, film.getRentalRate());
			st.setInt(7, film.getLength());
			st.setDouble(8, film.getReplacementCost());
			st.setString(9, film.getRating());
			st.setString(10, film.getSpecialFeatures());
			st.setInt(11, film.getId());
			System.out.println(film.getId());

			try {
				int uf = st.executeUpdate();
				conn.commit();
				System.out.println(uf + " film records updated");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Updated film ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during update.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public Actor addActor(Actor actor) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);
			String sql = "INSERT INTO actor (first_name, last_name) VALUES (?,?)";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, actor.getFirstName());
			st.setString(2, actor.getLastName());

			try {
				int aa = st.executeUpdate();
				conn.commit();
				System.out.println(aa + "film records updated");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Add Actor ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during insert.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public Actor addActorToFilm(Actor actor, Film film) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);
			String sql = "INSERT INTO film_actor (actor_id, film_id) VALUES (?,?)";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, actor.getId());
			st.setInt(2, film.getId());

			try {
				int af = st.executeUpdate();
				conn.commit();
				System.out.println(af + "film records updated");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Add Actor ID: " + keys.getInt(1) + "Add Film ID: " + keys.getInt(2));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during insert.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;

	}

	@Override
	public Actor updateActor(Actor actor) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);
			String sql = "UPDATE actor SET first_name = ?, last_name =? WHERE actor.id =?";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setString(1, actor.getFirstName());
			st.setString(2, actor.getLastName());
			st.setInt(3, actor.getId());

			try {
				int ua = st.executeUpdate();
				conn.commit();
				System.out.println(ua + "actor records updated");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Update Actor ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during update.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public Actor deleteActor(Actor actor) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);
			String sql = "DELETE actor FROM actor WHERE actor.id =?";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, actor.getId());

			try {
				int da = st.executeUpdate();
				conn.commit();
				System.out.println(da + "actor record deleted");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Delete Actor ID: " + keys.getInt(1));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during update.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public Actor deleteActorFromFilm(Actor actor, Film film) {
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(url, user, pword);
			conn.setAutoCommit(false);
			String sql = "DELETE FROM film_actor WHERE actor.id = ? AND film.id = ?";

			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			st.setInt(1, actor.getId());
			st.setInt(2, film.getId());

			try {
				int daf = st.executeUpdate();
				conn.commit();
				System.out.println(daf + "film actor records deleted");

				ResultSet keys = st.getGeneratedKeys();
				while (keys.next()) {
					System.out.println("Add Actor ID: " + keys.getInt(1) + "Add Film ID: " + keys.getInt(2));
				}
			} catch (SQLException e) {
				// Something went wrong.
				System.err.println("Error during insert.");
				// e.printStackTrace();
				System.err.println("SQL Error: " + e.getErrorCode() + ": " + e.getMessage());
				System.err.println("SQL State: " + e.getSQLState());
				// Need to rollback, which also throws SQLException.
				if (conn != null) {
					try {
						conn.rollback();
					} catch (SQLException e1) {
						System.err.println("Error rolling back.");
						e1.printStackTrace();
					}
				}
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;
	}

	@Override
	public List<InventoryItem> viewInventory() {
		// TODO Auto-generated method stub
		return null;
	}

//	public List<InventoryItem> viewInventory(Film film) {
//		List <InventoryItem> inventory = new ArrayList<>();
//		Connection conn = null;
//
////		TODO: update st.setLocalDateTime accordingly in front end to Date and Time.
//		try {
//			conn = DriverManager.getConnection(url, user, pword);
//			conn.setAutoCommit(false);
//			String sql = "SELECT id, film_id, store_id, media_condition, last_update FROM inventory_item WHERE film_id = ?";
//
//			PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//			
//			st.setInt(1, film.getId());
//
//			
//			try {
//				int vi = st.executeUpdate();
//				conn.commit();
//				System.out.println(vi + "inventory records found");
//
//				ResultSet keys = st.getGeneratedKeys();
//				while (keys.next()) {
//					System.out.println("Film ID Inventory: " + keys.getInt(1));
//				}
//
//		
//
//		
//		
//		return inventory;
//	}
}
