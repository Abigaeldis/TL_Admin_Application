package dal;

import java.util.List;

import bo.Composant;

public class PlatDAOFileImpl implements GenericDAO<Composant> {

	@Override
	public List<Composant> selectAll() throws DALException {
		// TODO Auto-generated method stub
		throw new DALException("Echec de l'ouverture du fichier", null);
	}

	@Override
	public Composant selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Composant composant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Composant composant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
