import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main (String[] args){
        ArrayList<Block> blockchain = new ArrayList<>();
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        Scanner scnr = new Scanner(System.in);


        //data1-data3 should be filled by the user
        System.out.println("Enter artefact: ");


/*
        Block genesisBlock = new Block(data1, blockchain.get(blockchain.size() - 1).getCurrHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        if (genesisBlock.getCurrHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        if (secondBlock.getCurrHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        Block newBlock = new Block(data3, blockchain.get(blockchain.size() - 1).getCurrHash(), new Date().getTime());
        newBlock.mineBlock(prefix);
        if (newBlock.getCurrHash().substring(0, prefix).equals(prefixString) && verify_Blockchain(ArrayList<Block> BC))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");
*/
    }
}
