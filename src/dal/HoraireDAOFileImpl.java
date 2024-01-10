package dal;

import java.util.List;

import bo.Horaire;

public class HoraireDAOFileImpl implements GenericDAO<Horaire> {

	@Override
	public List<Horaire> selectAll() throws DALException {
		// TODO Auto-generated method stub
		throw new DALException("Echec de l'ouverture du fichier", null);
	}

	@Override
	public Horaire selectById(int id) throws DALException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insert(Horaire composant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Horaire composant) throws DALException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) throws DALException {
		// TODO Auto-generated method stub
		
	}

}
