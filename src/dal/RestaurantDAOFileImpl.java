package dal;

import java.util.List;

import bo.Restaurant;

public class RestaurantDAOFileImpl implements GenericDAO<Restaurant> {

	@Override
	public List<Restaurant> selectAll() throws DALException {
		// TODO Auto-generated method stub
		throw new DALException("Echec de l'ouverture du fichier", null);
	}

	@Override
	public Restaurant selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Restaurant restaurant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Restaurant restaurant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
