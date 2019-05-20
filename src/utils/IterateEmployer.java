package utils;

import jobcatcher.EmployersAcc;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Scanner;

public class IterateEmployer {
    final static private Scanner scanner = new Scanner(System.in);
    private static IterateEmployer iterateEmployerInstance = null;

    private IterateEmployer() {
    }

    public static IterateEmployer getInstance() {
        if (iterateEmployerInstance == null)
            iterateEmployerInstance = new IterateEmployer();
        return iterateEmployerInstance;
    }

    int iterateList(CriteriaBuilder builder, Session session, String username, String password) {

        List<EmployersAcc> employersAccs = CreateLists.generics(builder, session, EmployersAcc.class);
        for (EmployersAcc eA : employersAccs) {
            if (eA.getUsername().equals(username) && eA.getPassword().equals(password)) {
                return eA.getId();
            }
        }
        return -1;
    }

}
