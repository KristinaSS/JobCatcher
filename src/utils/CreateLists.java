package utils;

import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CreateLists {
    private static CreateLists createListsInstance = null;

    private CreateLists() {
    }

    public static CreateLists getInstance() {
        if (createListsInstance == null)
            createListsInstance = new CreateLists();
        return createListsInstance;
    }

    static <T> List<T> generics(CriteriaBuilder builder, Session session, Class<T> clazz) {

        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        return session.createQuery(query).getResultList();
    }
}