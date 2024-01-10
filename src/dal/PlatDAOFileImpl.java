package dal;

import java.util.List;

import bo.Plat;

public class PlatDAOFileImpl implements GenericDAO<Plat> {

	@Override
	public List<Plat> selectAll() throws DALException {
		// TODO Auto-generated method stub
		throw new DALException("Echec de l'ouverture du fichier", null);
	}

	@Override
	public Plat selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Plat plat) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Plat plat) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
