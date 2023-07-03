# FilmQueryProject
The user is presented with a menu. They can choose to look up a film by it's id. The other option allows them to look up a film by a keyword within the title or description. When they look up a film, they are presented with the title, release year, rating, description, release year and all of the actors within the film. 

# Description
Within the DatabaseAccessorObject class I connected to the database. I then set up a prepared statement within each method to allow user input based off of the query needed to gather the right information. In the instance of the findFilmById method, I got information from the film table, and created a film object. Within each case inside the switch block, I called on methods that I needed to fulfill the users' requests, using the information within the objects created. 

# Technologies
-Java
-Eclipse
-sql(MAMP)


# Lessons Learned
I learned how to write methods that connect to a database.
I learned a lot about retrieval with JDBC. 
I learned about translating translating data within a database to attributes of an object in Java. 
I grew my understanding of calling methods within other methods. 
I practiced referring to objects' fields with "." notations. 
I practiced storing data within lists and objects.  
