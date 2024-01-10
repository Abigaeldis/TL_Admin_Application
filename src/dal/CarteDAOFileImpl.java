package dal;

import java.util.List;

import bo.Carte;

public class CarteDAOFileImpl implements GenericDAO<Carte> {

	@Override
	public List<Carte> selectAll() throws DALException {
		// TODO Auto-generated method stub
		throw new DALException("Echec de l'ouverture du fichier", null);
	}

	@Override
	public Carte selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Carte carte) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Carte carte) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
