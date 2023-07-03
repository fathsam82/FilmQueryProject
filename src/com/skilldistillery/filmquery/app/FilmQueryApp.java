package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() throws SQLException {
		Scanner input = new Scanner(System.in);
		boolean running = true;
		while (running) {
			System.out.println("Please choose a menu option: ");
			userMenu();
			String choice = input.nextLine();
			switch (choice) {
			case "1":
				System.out.println("Enter the film ID: ");
				int filmId = input.nextInt();
				input.nextLine();
				Film filmById = db.findFilmById(filmId);
				List<Actor> actors = db.findActorsByFilmId(filmId);
				if (filmById != null) {
					System.out.println("Title: " + filmById.getTitle());
					System.out.println("Release year: " + filmById.getReleaseYear());
					System.out.println("Rating: " + filmById.getRating());
					System.out.println("Description: " + filmById.getDescription());
					System.out.println("Language: " + filmById.getLanguageName());
					System.out.println("Actors: ");

					if (actors != null) {
						for (Actor actor : actors) {
							System.out.println(actor.getFirstName() + " " + actor.getLastName());

						}
					}

				} else {
					System.out.println("No results for that film id.");
				}
				break;

			case "2":
				System.out.println("Enter a keyword to look up a film: ");
				String filmKeyword = input.nextLine();
				List<Film> films = db.findFilmsByKeyword(filmKeyword);

				if (!films.isEmpty()) {
					for (Film film : films) {
						System.out.println("Title: " + film.getTitle());
						System.out.println("Release year: " + film.getReleaseYear());
						System.out.println("Rating: " + film.getRating());
						System.out.println("Description: " + film.getDescription());
						System.out.println("Language: " + film.getLanguageName());
						System.out.println("Actors:");

						if (film.getActors() != null) {
							for (Actor actor2 : film.getActors()) {
								System.out.println(actor2.toString());
							}

						}

						System.out.println();
					}
				} else {
					System.out.println("No results for that keyword.");
				}

				break;
			case "3":
				System.out.println("You have quit the program. Thank you for your searches.");
				running = false;
				default:
					System.out.println("Invalid selection");

			}

		}

		startUserInterface(input);
		input.close();
	}

	private void startUserInterface(Scanner input) {

	}

	public void userMenu() {
		System.out.println("=====MENU=====");
		System.out.println();
		System.out.println("1. Look up a film by it's id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
	}

}
