
//ID: A1
//Password: admin123

import service.VotingSystem;
import model.Voter;
import model.Admin;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        VotingSystem system = new VotingSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== MAIN MENU =====");
            System.out.println("1. Admin Login");
            System.out.println("2. Voter Login");
            System.out.println("3. View Candidates");
            System.out.println("4. View Results");
            System.out.println("5. Validate Blockchain");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice;

            try {
                choice = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                sc.nextLine(); // clear invalid input
                continue; // restart loop
            }

            switch (choice) {

                // =========================
                // ADMIN LOGIN
                // =========================
                case 1:
                    System.out.print("Enter Admin ID: ");
                    String adminId = sc.next();

                    System.out.print("Enter Password: ");
                    String adminPass = sc.next();

                    Admin admin = system.adminLogin(adminId, adminPass);

                    if (admin == null) {
                        System.out.println("Invalid admin credentials!");
                        break;
                    }

                    System.out.println("Admin login successful!");

                    // -------- ADMIN MENU --------
                    while (true) {
                        System.out.println("\n--- ADMIN MENU ---");
                        System.out.println("1. Register Voter");
                        System.out.println("2. Register Candidate");
                        System.out.println("3. View Candidates");
                        System.out.println("4. Logout");

                        System.out.print("Enter choice: ");
                        int adminChoice;

                        try {
                            adminChoice = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.nextLine(); // clear invalid input
                            continue; // restart loop
                        }

                        switch (adminChoice) {

                            case 1:
                                System.out.print("Enter Voter ID: ");
                                String vid = sc.next();

                                System.out.print("Enter Name: ");
                                String vname = sc.nextLine();

                                System.out.print("Enter Password: ");
                                String vpass = sc.next();

                                system.registerVoter(vid, vname, vpass);
                                System.out.println("Voter registered successfully!");
                                break;

                            case 2:
                                System.out.print("Enter Candidate ID: ");
                                String cid = sc.next();

                                System.out.print("Enter Name: ");
                                String cname = sc.nextLine();

                                system.registerCandidate(cid, cname);
                                System.out.println("Candidate registered successfully!");
                                break;

                            case 3:
                                system.displayCandidates();
                                break;

                            case 4:
                                System.out.println("Logging out...");
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }

                        if (adminChoice == 4) break;
                    }
                    break;

                // =========================
                // VOTER LOGIN
                // =========================
                case 2:
                    System.out.print("Enter Voter ID: ");
                    String voterId = sc.next();

                    System.out.print("Enter Password: ");
                    String voterPass = sc.next();

                    Voter voter = system.login(voterId, voterPass);

                    if (voter == null) {
                        System.out.println("Invalid credentials!");
                        break;
                    }

                    System.out.println("Login successful!");

                    // -------- VOTER MENU --------
                    while (true) {
                        System.out.println("\n--- VOTER MENU ---");
                        System.out.println("1. Cast Vote");
                        System.out.println("2. View Candidates");
                        System.out.println("3. Logout");

                        System.out.print("Enter choice: ");
                        int vChoice;

                        try {
                            vChoice = sc.nextInt();
                        } catch (Exception e) {
                            System.out.println("Invalid input! Please enter a number.");
                            sc.nextLine(); // clear invalid input
                            continue; // restart loop
                        }

                        switch (vChoice) {

                            case 1:
                                system.displayCandidates();

                                System.out.print("Enter Candidate ID: ");
                                String candidateId = sc.next();

                                system.castVote(voter.getId(), candidateId);
                                break;

                            case 2:
                                system.displayCandidates();
                                break;

                            case 3:
                                System.out.println("Logging out...");
                                break;

                            default:
                                System.out.println("Invalid choice!");
                        }

                        if (vChoice == 3) break;
                    }
                    break;

                // =========================
                // VIEW CANDIDATES
                // =========================
                case 3:
                    system.displayCandidates();
                    break;

                // =========================
                // VIEW RESULTS
                // =========================
                case 4:
                    system.displayResults();
                    break;

                // =========================
                // VALIDATE BLOCKCHAIN
                // =========================
                case 5:
                    system.validateBlockchain();
                    break;

                // =========================
                // EXIT
                // =========================
                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}