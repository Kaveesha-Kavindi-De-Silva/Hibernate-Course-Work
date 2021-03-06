package lk.ijse.hms.dao.custom.impl;

import lk.ijse.hms.dao.custom.UserCredentialDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import lk.ijse.hms.util.FactoryConfiguration;

import java.util.List;

public class UserCredentialDAOImpl implements UserCredentialDAO {
    @Override
    public List<String> getUserName() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT username FROM user_credential");
        List<String> username = query.list();

        transaction.commit();
        session.close();

        return username;
    }

    @Override
    public List<String> getPassWord() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("SELECT password FROM user_credential");
        List<String> password = query.list();

        transaction.commit();
        session.close();

        return password;
    }

    @Override
    public boolean update(String uName, String newUn, String newPw) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query query = session.createQuery("UPDATE user_credential SET username = : new_un, password = : new_pw WHERE username = : un");
        query.setParameter("new_un",newUn);
        query.setParameter("new_pw",newPw);
        query.setParameter("un",uName);
        int count = query.executeUpdate();

        transaction.commit();
        session.close();

        if(count > 0){
            return true;
        }
        return false;
    }
}
