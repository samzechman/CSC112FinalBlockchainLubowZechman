public class Stakeholder {

    //variable declarations
    private String id; //Id to identify each stakeholder
    private String name; //Name of the stakeholder
    private String address; //Address of the stakeholder
    private double balance; //Balance to keep track of the stakeholder's balance

    //setter methods
    public void setId (String id){
        this.id = id;
    }
    public void setName (String name){
        this.name = name;
    }
    public void setAddress (String address){
        this.address = address;
    }
    public void setBalance (double balance){
        this.balance = balance;
    }
    //getter methods
    public String getId (){
        return id;
    }
    public String getName (){
        return name;
    }
    public String getAddress (){
        return address;
    }
    public double getBalance (){
        return balance;
    }

    //to string
    @Override
    public String toString(){
        String string = "Stakeholder name: " + name + ", ID: " + id + ", Address: " + address + ", Balance: $" + balance;
        return string;
    }

}
