package service;

import model.*;
import blockchain.*;

import java.util.*;

public class VotingSystem {

    private List<Voter> voters;
    private List<Candidate> candidates;
    private Blockchain blockchain;

    public VotingSystem() {
        voters = new ArrayList<>();
        candidates = new ArrayList<>();
        blockchain = new Blockchain();
    }


    // REGISTRATION

    public void registerVoter(String id, String name, String password) {
        voters.add(new Voter(id, name, password));
    }

    public void registerCandidate(String id, String name) {
        candidates.add(new Candidate(id, name));
    }

    // LOGIN

    public Voter login(String id, String password) {
        for (Voter v : voters) {
            if (v.login(id, password)) {
                return v;
            }
        }
        return null;
    }

    // VOTING

    public void castVote(String voterId, String candidateId) {

        Voter voter = null;

        // Find voter
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

        // Check candidate exists
        boolean candidateExists = false;
        for (Candidate c : candidates) {
            if (c.getId().equals(candidateId)) {
                candidateExists = true;
                break;
            }
        }

        if (!candidateExists) {
            System.out.println("Invalid candidate!");
            return;
        }

        // Create vote
        Vote vote = new Vote(voterId, candidateId);

        // Add to blockchain
        blockchain.addBlock(vote.toString());

        // Mark voter
        voter.setVoted(true);

        System.out.println("Vote cast successfully!");
    }

    // RESULTS


    public void displayResults() {
        Map<String, Integer> voteCount = new HashMap<>();

        // Initialize counts
        for (Candidate c : candidates) {
            voteCount.put(c.getId(), 0);
        }

        // Traverse blockchain
        for (Block block : blockchain.getChain()) {
            String data = block.getData();

            if (data.equals("Genesis Block")) continue;

            String[] parts = data.split("->");
            String candidateId = parts[1];

            voteCount.put(candidateId, voteCount.get(candidateId) + 1);
        }

        // Display
        System.out.println("===== RESULTS =====");
        for (Candidate c : candidates) {
            System.out.println(
                c.getName() + " : " + voteCount.get(c.getId()) + " votes"
            );
        }
    }

    // VALIDATION
    public void validateBlockchain() {
        if (blockchain.isChainValid()) {
            System.out.println("Blockchain is VALID");
        } else {
            System.out.println("Blockchain is TAMPERED!");
        }
    }

    //  VIEW


    public void displayCandidates() {
        for (Candidate c : candidates) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }
}