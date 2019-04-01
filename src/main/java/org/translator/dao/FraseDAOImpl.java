package org.translator.dao;

import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.translator.model.Frase;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.translator.utils.SessionUtil;

import java.util.List;

@Component
public class FraseDAOImpl extends SessionUtil implements FraseDAO{

    public List<Frase> getAll() {
        openTransactionSession();
        Session session = getSession();
        Query<Frase> query = session.createQuery("from Frase", Frase.class);
        List<Frase> list = query.list();
        closeTransactionSesstion();
        return list;
    }

    public Frase getOne(String text) {
        openTransactionSession();
        Session session = getSession();
        System.out.println(session.isConnected() + " " + session.isOpen());
        Query<Frase> q = session.createQuery(
                "from Frase where text = :text", Frase.class);
        Frase frase = q.list().stream().findAny().orElse(null);
        closeTransactionSesstion();
        return frase;
    }

    public void add(Frase frase) {
        openTransactionSession();
        Session session = getSession();
        session.saveOrUpdate(frase);
        closeTransactionSesstion();
    }
}
