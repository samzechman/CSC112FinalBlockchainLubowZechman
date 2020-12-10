
public class Transaction {

    //variable declarations
    private Artefact artefact; //Artefact of type Artefact class
    private int timestamp; //Timestamp (year) of the transaction on the artefact identified with id including the date and time
    private Stakeholder seller; //the seller in this current transaction of type Stakeholder
    private Stakeholder buyer; //the buyer in this current transaction of type Stakeholder
    private Stakeholder auctionHouse; //AuctionHouse of type Stakeholder
    private double price; //the price of the artefact in this current transaction

    //default constructor
    public Transaction (){
        artefact = null;
        timestamp = 0;
        seller = null;
        buyer = null;
        auctionHouse = null;
        price = 0;
    }

    //copy constructor
    public Transaction (Transaction t){
        this.artefact = t.artefact;
        this.timestamp = t.timestamp;
        this.seller = t.seller;
        this.buyer = t.buyer;
        this.auctionHouse = t.auctionHouse;
        this.price = t.price;
    }


    //setter methods
    public void setArtefact (Artefact artefact){
        this.artefact = artefact;
    }
    public void setTimestamp (int timestamp){
        this.timestamp = timestamp;
    }
    public void setSeller (Stakeholder seller){
        this.seller = seller;
    }
    public void setBuyer (Stakeholder buyer){
        this.buyer = buyer;
    }
    public void setAuctionHouse (Stakeholder auctionHouse){
        this.auctionHouse = auctionHouse;
    }
    public void setPrice (double price){
        this.price = price;
    }
    //getter methods
    public Artefact getArtefact (){
        return artefact;
    }
    public int getTimestamp (){
        return timestamp;
    }
    public Stakeholder getSeller (){
        return seller;
    }
    public Stakeholder getBuyer (){
        return buyer;
    }
    public Stakeholder getAuctionHouse (){
        return auctionHouse;
    }
    public double getPrice (){
        return price;
    }

    //to string
    @Override
    public String toString(){
        String string = artefact.toString() + ", Time stamp: " + timestamp + ", Seller: " + seller.getName() + ", Buyer: " + buyer.getName()
                + ", Auction house: " + auctionHouse.getAddress() + ", Price: $" + price;
        return string;
    }



}
