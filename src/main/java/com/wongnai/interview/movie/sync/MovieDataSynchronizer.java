package com.wongnai.interview.movie.sync;

import javax.transaction.Transactional;

import com.wongnai.interview.movie.InvertedIndexMap;
import com.wongnai.interview.movie.Movie;
import com.wongnai.interview.movie.external.MovieData;
import com.wongnai.interview.movie.external.MoviesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wongnai.interview.movie.MovieRepository;
import com.wongnai.interview.movie.external.MovieDataService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

@Component
public class MovieDataSynchronizer {
	@Autowired
	private MovieDataService movieDataService;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private InvertedIndexMap map ;

	@Transactional
	public void forceSync() {
		//TODO: implement this to sync movie into repository
		MoviesResponse response = movieDataService.fetchAll();
		Iterator<MovieData> itrMovie = response.iterator();
		int index = 1;
		while(itrMovie.hasNext()){
			MovieData data = itrMovie.next();
			Movie movie = new Movie(data.getTitle());
			movie.setActors(data.getCast());
			movie.setId((long)index);
			movieRepository.save(movie);
			createIndex(movie);
			index++;
		}
	}

	private void createIndex(Movie movie){
		for(String s : movie.getName().split(" ")){
			if(map.getMap().containsKey(s.toLowerCase())){
				map.getMap().get(s.toLowerCase()).add(movie.getId());
			}else{
				HashSet<Long> set = new HashSet<>();
				set.add(movie.getId());
				map.getMap().put(s.toLowerCase(), set);
			}
		}
	}

}
