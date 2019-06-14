package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.List;

import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MovieDataServiceImpl;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieSearchService;
import com.wongnai.interview.movie.external.MovieDataService;

@Component("simpleMovieSearchService")

public class SimpleMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieDataService movieDataService;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 2 => Implement this method by using data from MovieDataService
		// All test in SimpleMovieSearchServiceIntegrationTest must pass.
		// Please do not change @Component annotation on this class

		List<Movie> result = new ArrayList<>();
		MoviesResponse response = movieDataService.fetchAll();

		if(!queryText.isEmpty()){
			for (MovieData i : response){
				if(iscontain(i.getTitle(),queryText)){
					Movie movie = new Movie(i.getTitle());
					List<String> actors = new ArrayList<>(i.getCast());
					movie.setActors(actors);
					result.add(movie);
				}
			}
		}
		return result;
	}

	public boolean iscontain (String sentence, String queryText){
		boolean check = false ;
		String[] word = sentence.split(" ");
		for(String w :word){
			if((w.toLowerCase().equals(queryText.toLowerCase())) ){
				check = true ;
				break;
			}
		}
		return check;
	}
}
