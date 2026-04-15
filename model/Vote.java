package model;
public class Vote {
    private String voterId;
    private String candidateId;

    public Vote(String voterId , String candidateId){
        this.voterId = voterId;
        this.candidateId = candidateId;
    }
    public String getVoterId() {
        return voterId;
    }

    public String getCandidateId() {
        return candidateId;
    }
    @Override
    public String toString() {
        return voterId + "->" + candidateId;
    }

}
