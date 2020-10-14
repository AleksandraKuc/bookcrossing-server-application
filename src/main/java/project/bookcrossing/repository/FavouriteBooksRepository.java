package project.bookcrossing.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.bookcrossing.entity.FavouriteBooks;
import project.bookcrossing.entity.FavouritesKey;

import java.util.List;

@Repository
public interface FavouriteBooksRepository extends CrudRepository<FavouriteBooks, FavouritesKey> {
	List<FavouriteBooks> findAll(Example<FavouriteBooks> favouriteExample);
}
