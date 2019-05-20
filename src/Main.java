import org.hibernate.Session;
import utils.HibernateUtil;
import utils.Methods;

import javax.persistence.criteria.CriteriaBuilder;


public class Main {

    public static void main(final String[] args) throws Exception {
        Session session = HibernateUtil.getSession();
        Methods methods = Methods.getInstance();
        CriteriaBuilder builder = session.getCriteriaBuilder();

        int ID = 0;
        int resHome = methods.home();
        int resMenu;
        switch (resHome) {
            case 1:
                ID = methods.login(builder, session);
                break;

            case 2:
                ID = methods.addNewEmployer(session, builder);
                break;

            case 3:
                while (true) {
                    switch (methods.printMenu(3)) {
                        case 1:
                            methods.applyForJob(session, builder);
                            continue;
                        case 2:
                            methods.byCatagory(session, builder, methods.printCat(session, builder));
                            continue;
                        case 3:
                            System.exit(0);
                    }
                }


        }
        if (resHome == 1 || resHome == 2) {
            while (true) {
                switch (methods.printMenu(1)) {
                    case 1:
                        methods.addAdvertisment(ID, session, builder);
                        continue;
                    case 2:
                        methods.byCatagory(session, builder, methods.printCat(session, builder));
                        continue;
                    case 3:
                        break;
                }
                break;
            }

        }


    }
}