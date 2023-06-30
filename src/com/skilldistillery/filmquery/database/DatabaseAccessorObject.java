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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		return null;
	}
	


	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String sql = "  ";
		Connection conn = DriverManager.getConnection(URL, userName, password);
		PreparedStatement pstmt = conn.prepareStatement(sql);
		System.out.println(pstmt);
		
		pstmt.setInt(1, filmId);
		ResultSet filmResult = pstmt.executeQuery();
		
		
		
		
	
	
	
	
	
	
	
	
	
		return film;
	}
	
	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";

		// 1) connect the the db
		Connection conn = DriverManager.getConnection(URL, userName, password);
		// 2) prepare the SQL statement(compile/optimize)
		PreparedStatement pstmt = conn.prepareStatement(sql);

		// 2a) debugging
		System.out.println(pstmt);

		// 3) bind the value, actorId to the bind variable, ?
		pstmt.setInt(1, actorId);

		// 4)ResultSet actorResult = pstmt.executeQuery();
		ResultSet actorResult = pstmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor();
//          OR
//		    actor.setId(actorResult.getInt("id"));
//		    actor.setFirstName(actorResult.getString("first_name"));
//		    actor.setLastName(actorResult.getString("last_name"));
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
			actorResult.getString("last_name"));
			actor.setFilms(findFilmsByActorId(actorId));

		}

		actorResult.close();
		pstmt.close();
		conn.close();

		return actor;

	}

	

	@Override
	public List<Film> findFilmsByActorId(int actorId) {
		List<Film> films = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, userName, password);
			String sql = "SELECT film.*"
					+ " FROM film JOIN film_actor ON film.id = film_actor.film_id " + " WHERE actor_id = ?";
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
				Film film = new Film(filmId, title, desc, releaseYear, rentDur, rate, length, repCost, rating, features);
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
