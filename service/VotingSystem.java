package service;

import java.io.*;
import model.*;
import blockchain.*;

import java.util.*;

public class VotingSystem {

    // FILE PATHS
    private final String VOTER_FILE = "data/voters.txt";
    private final String CANDIDATE_FILE = "data/candidates.txt";

    private List<Admin> admins;
    private List<Voter> voters;
    private List<Candidate> candidates;
    private Blockchain blockchain;

    // CONSTRUCTOR
    public VotingSystem() {
        voters = new ArrayList<>();
        candidates = new ArrayList<>();
        blockchain = new Blockchain();
        admins = new ArrayList<>();

        admins.add(new Admin("A1", "Admin", "admin123"));

        // LOAD DATA FROM FILE
        loadVotersFromFile();
        loadCandidatesFromFile();
    }

    // =========================
    // REGISTRATION
    // =========================

    public void registerVoter(String id, String name, String password) {

        if (voterExists(id)) {
            System.out.println("Voter ID already exists!");
            return;
        }

        voters.add(new Voter(id, name, password));
        saveVotersToFile(); // SAVE
    }

    public void registerCandidate(String id, String name) {

        if (candidateExists(id)) {
            System.out.println("Candidate ID already exists!");
            return;
        }

        candidates.add(new Candidate(id, name));
        saveCandidatesToFile(); // SAVE
    }

    // =========================
    // HELPER METHODS
    // =========================

    private boolean voterExists(String id) {
        for (Voter v : voters) {
            if (v.getId().equals(id)) return true;
        }
        return false;
    }

    private boolean candidateExists(String id) {
        for (Candidate c : candidates) {
            if (c.getId().equals(id)) return true;
        }
        return false;
    }

    // =========================
    // LOGIN
    // =========================

    public Voter login(String id, String password) {
        for (Voter v : voters) {
            if (v.login(id, password)) return v;
        }
        return null;
    }

    public Admin adminLogin(String id, String password) {
        for (Admin a : admins) {
            if (a.login(id, password)) return a;
        }
        return null;
    }

    // =========================
    // VOTING
    // =========================

    public void castVote(String voterId, String candidateId) {

        Voter voter = null;

        for (Voter v : voters) {
            if (v.getId().equals(voterId)) {
                voter = v;
                break;
            }
        }

        if (voter == null) {
            System.out.println("Voter not found!");
            return;
        }

        if (voter.hasVoted()) {
            System.out.println("You have already voted!");
            return;
        }

        boolean exists = false;
        for (Candidate c : candidates) {
            if (c.getId().equals(candidateId)) {
                exists = true;
                break;
            }
        }

        if (!exists) {
            System.out.println("Invalid candidate!");
            return;
        }

        // CREATE VOTE
        Vote vote = new Vote(voterId, candidateId);

        // ADD BLOCK
        blockchain.addBlock(vote.toString());

        // MARK VOTED
        voter.setVoted(true);

        saveVotersToFile(); // SAVE voted status

        System.out.println("Vote cast successfully!");
    }

    // =========================
    // RESULTS
    // =========================

    public void displayResults() {

        Map<String, Integer> voteCount = new HashMap<>();

        for (Candidate c : candidates) {
            voteCount.put(c.getId(), 0);
        }

        for (Block block : blockchain.getChain()) {

            String data = block.getData();

            if (data.equals("Genesis Block")) continue;

            try {
                String[] parts = data.split("->");
                String candidateId = parts[1];

                voteCount.put(candidateId,
                        voteCount.getOrDefault(candidateId, 0) + 1);

            } catch (Exception e) {
                System.out.println("Invalid block data detected!");
            }
        }

        System.out.println("===== RESULTS =====");
        for (Candidate c : candidates) {
            System.out.println(
                    c.getName() + " : " + voteCount.get(c.getId()) + " votes"
            );
        }
    }

    // =========================
    // BLOCKCHAIN VALIDATION
    // =========================

    public void validateBlockchain() {
        if (blockchain.isChainValid()) {
            System.out.println("Blockchain is VALID");
        } else {
            System.out.println("Blockchain is TAMPERED!");
        }
    }

    // =========================
    // DISPLAY
    // =========================

    public void displayCandidates() {
        for (Candidate c : candidates) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }

    public void displayVoters() {
        for (Voter v : voters) {
            System.out.println(v.getId() + " - " + v.getName());
        }
    }

    // =========================
    // FILE HANDLING
    // =========================

    public void saveVotersToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(VOTER_FILE))) {

            for (Voter v : voters) {
                bw.write(v.getId() + "," +
                        v.getName() + "," +
                        v.getPassword() + "," +
                        v.hasVoted());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving voters!");
        }
    }

    public void loadVotersFromFile() {

        voters.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(VOTER_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                Voter v = new Voter(data[0], data[1], data[2]);

                if (Boolean.parseBoolean(data[3])) {
                    v.setVoted(true);
                }

                voters.add(v);
            }

        } catch (IOException e) {
            System.out.println("No voter file found. Starting fresh.");
        }
    }

    public void saveCandidatesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CANDIDATE_FILE))) {

            for (Candidate c : candidates) {
                bw.write(c.getId() + "," + c.getName());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error saving candidates!");
        }
    }

    public void loadCandidatesFromFile() {

        candidates.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(CANDIDATE_FILE))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                candidates.add(new Candidate(data[0], data[1]));
            }

        } catch (IOException e) {
            System.out.println("No candidate file found.");
        }
    }
}