package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private static final String userName = "student";
	private static final String password = "student";

	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String sql = " SELECT film.* FROM film WHERE film.id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, filmId);
			ResultSet filmResult = pstmt.executeQuery();
			while (filmResult.next()) {
				int id = filmResult.getInt("id");
				String title = filmResult.getString("title");
				String desc = filmResult.getString("description");
				int releaseYear = filmResult.getShort("release_year");
				int langId = filmResult.getInt("language_id");
				int rentDur = filmResult.getInt("rental_duration");
				double rate = filmResult.getDouble("rental_rate");
				int length = filmResult.getInt("length");
				double repCost = filmResult.getDouble("replacement_cost");
				String rating = filmResult.getString("rating");
				String features = filmResult.getString("special_features");
				String languageName = findLanguageNameById(langId);

				film = new Film(id, title, desc, releaseYear, rentDur, rate, length, repCost, rating, features,
						languageName);
				// film.set
			}
			filmResult.close();
			pstmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return film;
	}

	@Override
	public String findLanguageNameById(int languageId) throws SQLException {
		String languageName = "";
		String sql = " SELECT language.* FROM language WHERE language.id = ?";
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			PreparedStatement pstmt = conn.prepareStatement(sql);	
			pstmt.setInt(1, languageId);
			ResultSet filmResult = pstmt.executeQuery();
			while (filmResult.next()) {
				languageName = filmResult.getString("name");
				
			

			}
			filmResult.close();
			pstmt.close();
			conn.close();
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
		return languageName;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
	try {
		Connection conn = DriverManager.getConnection(URL, userName, password);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, actorId);
		ResultSet actorResult = pstmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor();
			
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
			actorResult.getString("last_name"));
			actor.setFilms(findFilmsByActorId(actorId));

		}

		actorResult.close();
		pstmt.close();
		conn.close();
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return actor;

	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			String sql = "SELECT actor.*" + "FROM actor JOIN film_actor ON actor.id = film_actor.actor_id "
			+ " WHERE film_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				Actor actor = new Actor(id, firstName, lastName);
				actors.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;

	}

	@Override
	public List<Film> findFilmsByActorId(int actorId) throws SQLException {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			String sql = "SELECT film.*" + " FROM film JOIN film_actor ON film.id = film_actor.film_id "
			+ " WHERE actor_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				int releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				String languageName = rs.getString("name");

				Film film = new Film(filmId, title, desc, releaseYear, rentDur, rate, length, repCost, rating, features,
						languageName);
				films.add(film);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;
	}

	@Override
	public List<Film> findFilmsByKeyword(String keyword) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			String sql = "SELECT film.* FROM film " + " WHERE film.title LIKE ? OR film.description LIKE ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + keyword + "%");
			stmt.setString(2, "%" + keyword + "%");
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int filmId = rs.getInt("id");
				String title = rs.getString("title");
				String desc = rs.getString("description");
				int releaseYear = rs.getShort("release_year");
				int langId = rs.getInt("language_id");
				int rentDur = rs.getInt("rental_duration");
				double rate = rs.getDouble("rental_rate");
				int length = rs.getInt("length");
				double repCost = rs.getDouble("replacement_cost");
				String rating = rs.getString("rating");
				String features = rs.getString("special_features");
				String languageName = findLanguageNameById(langId);
				List<Actor> findActorsById = findActorsByFilmId(filmId);

				Film film = new Film(filmId, title, desc, releaseYear, rentDur, rate, length, repCost, rating, features,
						languageName);
				film.setActors(findActorsById);
				films.add(film);

			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return films;

	}

}