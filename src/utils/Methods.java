package utils;

import jobcatcher.Advertisments;
import jobcatcher.Candidate;
import jobcatcher.Catagory;
import jobcatcher.EmployersAcc;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Methods {
    private static Scanner scanner = new Scanner(System.in);

    private static Methods methodsInstance = null;

    private Methods() {
    }

    public static Methods getInstance() {
        if (methodsInstance == null)
            methodsInstance = new Methods();
        return methodsInstance;
    }

    public void addAdvertisment(int EmployerID, Session session, CriteriaBuilder builder) {
        try {

            Transaction tx = session.beginTransaction();

            List<EmployersAcc> employersAccs = CreateLists.generics(builder, session, EmployersAcc.class);

            List<Catagory> catagories = CreateLists.generics(builder, session, Catagory.class);

            for (EmployersAcc eA : employersAccs) {
                if (eA.getId() == EmployerID) {
                    if (eA.getNumActiveAdds() == 10) {
                        System.out.println("You cannot post anymore adds, you have reached your active adds limit!");
                        if (tx.isActive())
                            session.getTransaction().commit();
                        return;
                    }

                    while (true) {
                        System.out.println("What catagory would you like your ad to be?");

                        for (Catagory c : catagories) {
                            System.out.println(c.getId() + "     " + c.getName());
                        }
                        int CatagoryID = scanner.nextInt();
                        if (CatagoryID < 0 || CatagoryID > catagories.size())
                            continue;


                        scanner.nextLine();

                        System.out.println("What will be the heading:");
                        String heading = scanner.nextLine();


                        Advertisments advertisment = new Advertisments(true);
                        advertisment.setHeading(heading);

                        eA.getAdvertisments().add(advertisment);
                        eA.setNumActiveAdds(eA.getNumActiveAdds() + 1);
                        eA.setTotalAdvertisments(eA.getTotalAdvertisments() + 1);

                        for (Catagory c : catagories) {
                            if (c.getId() == CatagoryID) {
                                c.getAdvertisments().add(advertisment);
                            }
                        }

                        session.save(eA);
                        break;
                    }
                    break;
                }
            }


            if (tx.isActive())
                session.getTransaction().commit();

            System.out.println("Successfully Added your advertisment");

        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }

    }

    public int addNewEmployer(Session session, CriteriaBuilder builder) {
        try {

            Transaction tx = session.beginTransaction();
            IterateEmployer iterateClient = IterateEmployer.getInstance();
            int id;
            while (true) {
                scanner.nextLine();
                System.out.println("Username");
                String username = scanner.nextLine();

                //scanner.nextLine();

                System.out.println("email");
                String password = scanner.nextLine();

                id = iterateClient.iterateList(builder, session, username, password);
                if (id > 0) {
                    System.out.println("A user with this username and email already exists!" +
                            "Try again");
                    continue;
                }

                System.out.println("First Name:");
                String fName = scanner.nextLine();

                System.out.println("Last Name:");
                String lName = scanner.nextLine();


                EmployersAcc employersAcc = new EmployersAcc(fName, lName, username, password, 0, 0);

                session.save(employersAcc);

                if (tx.isActive())
                    session.getTransaction().commit();

                System.out.println("Successfully Added new Employer");

                return employersAcc.getId();
            }

        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }

        return -1;

    }

    public void applyForJob(Session session, CriteriaBuilder builder) {
        try {

            Transaction tx = session.beginTransaction();

            List<Advertisments> advertisments = CreateLists.generics(builder, session, Advertisments.class);

            scanner.reset();
            scanner.nextLine();
            System.out.println("First Name:");
            String fName = scanner.nextLine();

            //scanner.nextLine();

            System.out.println("Last Name:");
            String lName = scanner.nextLine();

            System.out.println("email:");
            String email = scanner.nextLine();

            printAd(session, builder);

            int addID = scanner.nextInt();

            for (Advertisments ad : advertisments) {
                if (ad.getId() == addID) {
                    for (Candidate c : ad.getCandidates()) {
                        if (c.getEmail().equals(email)) {
                            System.out.println("You have already applied for this ad once!");
                            if (tx.isActive())
                                session.getTransaction().commit();
                            return;
                        }
                    }
                    Candidate candidate = new Candidate(fName, lName, addID, email);
                    ad.getCandidates().add(candidate);
                    candidate.getAdvertisments().add(ad);

                    session.save(candidate);
                    if (tx.isActive())
                        session.getTransaction().commit();
                    System.out.println("Successfully applied");
                    return;
                }
            }
            System.out.println("Advertisment Doesnt exist");
            throw new Exception();


        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }

    }

    public void byCatagory(Session session, CriteriaBuilder builder, int catagoryID) {
        try {

            Transaction tx = session.beginTransaction();

            List<Candidate> candidates = new ArrayList<>();
            List<Catagory> catagories = CreateLists.generics(builder, session, Catagory.class);

            int counterActive = 0;
            int flag = 0;


            for (Catagory c : catagories) {
                if (c.getId() == catagoryID) {
                    for (Advertisments a : c.getAdvertisments()) {
                        if (a.isActive())
                            counterActive++;

                        for (Candidate candidate : a.getCandidates()) {
                            for (Candidate candidate1 : candidates) {
                                if (candidate.getEmail().equals(candidate1.getEmail())) {
                                    flag = 1;
                                    break;
                                }

                            }
                            if (flag == 0)
                                candidates.add(candidate);
                            flag = 0;
                        }
                    }
                }
            }

            System.out.println("Number of candidates(active and inactive): " + candidates.size());
            System.out.println("Number of active ads in this catagory: " + counterActive);

            if (tx.isActive())
                session.getTransaction().commit();

        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }
    }

    public int home() {
        int resHome;
        do {
            System.out.println("If you are an employer press:");
            System.out.println("Enter 1 to log in and 2 to sign up");
            System.out.println("If you are looking for a job: enter 3");
            resHome = scanner.nextInt();
        } while (resHome < 1 || resHome > 3);
        return resHome;
    }

    public int printMenu(int res) {

        assert res ==1|| res == 2 || res ==3: "Wrong choice";

        System.out.println("Choose:");
        System.out.println();
        List<String> menu = new ArrayList<>();
        if (res == 1 || res == 2) {
            menu.add("Menu");
            menu.add("1. Add Advertisement");
            menu.add("2. See number of People and Active Ads by Catagory");
            menu.add("3. Exit");
        } else {
            menu.add("Menu");
            menu.add("1. Apply for Job");
            menu.add("2. See number of People and Active Ads by Catagory");
            menu.add("3. Exit");
        }

        for (String string : menu)
            System.out.println(string);

        return scanner.nextInt();
    }

    public int login(CriteriaBuilder builder, Session session) {
        Transaction tx = session.beginTransaction();

        IterateEmployer iterateClient = IterateEmployer.getInstance();

        scanner.nextLine();
        System.out.println("Username");
        String username = scanner.nextLine();

        System.out.println("Password");
        String password = scanner.nextLine();

        int id = iterateClient.iterateList(builder, session, username, password);
        if (id > 0) {
            System.out.println("Welcome to JobCatcher Choose one of the following:");

            if (tx.isActive())
                session.getTransaction().commit();
            return id;
        }
        System.out.println("No account exists with this Username and password");

        if (tx.isActive())
            session.getTransaction().commit();

        System.exit(0);
        return -1;
    }

    private void printAd(Session session, CriteriaBuilder builder) {
        try {

            List<Advertisments> advertisments = CreateLists.generics(builder, session, Advertisments.class);

            for (Advertisments a : advertisments) {
                if (a.isActive()) {
                    System.out.println(a.getId() + "  " + a.getHeading());
                }
            }

        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }

    }

    public int printCat(Session session, CriteriaBuilder builder) {
        try {

            Transaction tx = session.beginTransaction();

            List<Catagory> catagories = CreateLists.generics(builder, session, Catagory.class);

            for (Catagory a : catagories) {
                System.out.println(a.getId() + "   " + a.getName());
            }
            int res;
            while (true) {
                res = scanner.nextInt();

                if (res < 1 || res > catagories.size()) {
                    continue;
                }
                break;
            }

            if (tx.isActive())
                session.getTransaction().commit();
            return res;

        } catch (Exception e) {
            System.out.println("Exception caught!");
            session.getTransaction().rollback();
        }
        return -1;

    }

}