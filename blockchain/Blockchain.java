import java.util.ArrayList;
import java.util.List;

public class Blockchain {
    private List<Block> chain;//Block0 → Block1 → Block2 → Block3 ...
    //create blockchain
    public Blockchain() {
        chain = new ArrayList<>();
        chain.add(createGenesisBlock());
    }
    //First block has no previous block
    //Blockchain must always start somewhere
    private Block createGenesisBlock() {
        return new Block(0, "Genesis Block", "0");
    }

    public Block getLatestBlock() {
        return chain.get(chain.size() - 1);
    }

    public void addBlock(String data) {
        Block previousBlock = getLatestBlock();
        Block newBlock = new Block(
                chain.size(),
                data,
                previousBlock.getHash()
        );
        chain.add(newBlock);
    }

    public List<Block> getChain() {
        return chain;
    }

    public boolean isChainValid() {
        for (int i = 1; i < chain.size(); i++) {
            Block current = chain.get(i);
            Block previous = chain.get(i - 1);

            // Check hash integrity
            if (!current.getHash().equals(current.calculateHash())) {
                return false;
            }

            // Check linking
            if (!current.getPreviousHash().equals(previous.getHash())) {
                return false;
            }
        }
        return true;
    }

    public void displayChain() {
        for (Block block : chain) {
            System.out.println("Data: " + block.getData());
            System.out.println("Hash: " + block.getHash());
            System.out.println("PrevHash: " + block.getPreviousHash());
            System.out.println("---------------------------");
        }
    }
}
