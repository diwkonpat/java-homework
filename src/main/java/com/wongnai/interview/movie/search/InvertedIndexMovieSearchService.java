package com.wongnai.interview.movie.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.wongnai.interview.movie.InvertedIndexMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.MovieSearchService;

@Component("invertedIndexMovieSearchService")
@DependsOn("movieDatabaseInitializer")
public class InvertedIndexMovieSearchService implements MovieSearchService {
	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private InvertedIndexMap map ;

	@Override
	public List<Movie> search(String queryText) {
		//TODO: Step 4 => Please implement in-memory inverted index to search movie by keyword.
		// You must find a way to build inverted index before you do an actual search.
		// Inverted index would looks like this:
		// -------------------------------
		// |  Term      | Movie Ids      |
		// -------------------------------
		// |  Star      |  5, 8, 1       |
		// |  War       |  5, 2          |
		// |  Trek      |  1, 8          |
		// -------------------------------
		// When you search with keyword "Star", you will know immediately, by looking at Term column, and see that
		// there are 3 movie ids contains this word -- 1,5,8. Then, you can use these ids to find full movie object from repository.
		// Another case is when you find with keyword "Star War", there are 2 terms, Star and War, then you lookup
		// from inverted index for Star and for War so that you get movie ids 1,5,8 for Star and 2,5 for War. The result that
		// you have to return can be union or intersection of those 2 sets of ids.
		// By the way, in this assignment, you must use intersection so that it left for just movie id 5.

		List<Movie> list = new ArrayList<>();
		HashMap<Integer, Integer> temp = new HashMap<>();
		int max = Integer.MIN_VALUE;
		for(String str : queryText.split(" ")) {
			if (map.getMap().containsKey(str.toLowerCase())) {
				Iterator<Long> itr = map.getMap().get(str.toLowerCase()).iterator();
				while (itr.hasNext()) {
					Long num = itr.next();
					if (temp.containsKey(num.intValue())) temp.put(num.intValue(), temp.get(num.intValue()) + 1);
					else temp.put(num.intValue(), 0);

					if (max < temp.get(num.intValue())) max = temp.get(num.intValue());
				}
			}
		}
		Iterator<Integer> itrIn = temp.keySet().iterator();
		while(itrIn.hasNext()){
			int num = itrIn.next();
			if(temp.get(num) == max) list.add(movieRepository.findByIdContains((long)num).get(0));

		}
		Iterator<Movie> itrMovie = list.iterator();
		return list;
	}
}
