import java.util.Scanner; //use scanner to get input
import java.time.LocalDate; // import the LocalDate class 
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList; // import the ArrayList class
import java.io.FileWriter;   // Import the FileWriter class
import java.io.BufferedWriter; //write to a file while keeping what's there



public class project1 {
  public static void main(String[ ] args){   
    LoginOrSignUp loginorsignup = new LoginOrSignUp();
    Username username = new Username();
    Login login = new Login();
    SignUp signup = new SignUp();
    ViewAccount viewaccount = new ViewAccount();
    CheckingOut checkout = new CheckingOut();
    DisplayAccount display = new DisplayAccount();

    String u = "null";
    int i = loginorsignup.readingInput();

    while (i > 2){    
      System.out.println("There was an error with the input please try again\n");
      i = loginorsignup.readingInput();
    }
    
    if (i == 1){
      u = login.existingUser();

    }
    else if(i == 2){
      u = signup.getNewLogin();    //want to create a login
    }

    username.setUsername(u); //set the username based on a correct input


    int x = viewaccount.readInput();
    while (x > 2){
      System.out.println("There was an error with the input please try again\n");
      x = viewaccount.readInput();
    }

    if(x == 1){
      checkout.GetBookTitle(u);    //the user will have the chance to checkout a new book
    } 
    else if (x == 2){
      display.readfile(u);    //the user gets to view their account

}
}
}


class ViewAccount{
/*This class asks for user input to decide if they want to check out a book 
 * view their checkout history
*/

  public String getInput(){
  /*getInput reads and returns the user input. It then passes it to readInput */
    Scanner read = new Scanner(System.in);
    System.out.println("Press \'3\' to checkout a book \nPress \'4\' to view account history");
    String input = read.nextLine();
    return input;
  }
  public int readInput(){
  /* readInput reads the input from getInput. It should be either '3' or '4' and if it is
   * not then it loops until an acceptable input is given
   */

    String Uinput = getInput();

    if (Uinput.equals("3")){
        return 1;
    }

    else if (Uinput.equals("4")){
        return 2;
    }
    else {
      // something was inputed that wasn't a 3 or 4
      return 3;
    }
  }
}


class DisplayAccount{
/* The purpose of DisplayAccount is to read the user specific file. Each user gets 
 * an account in the form of a txt file created for them as soon as they sign up for an account
 * This class is called in ViewAccount when the user inputs '4'
 */
  public void readfile(String user){

    try {
      File myObj = new File(user + ".txt");
      Scanner myReader = new Scanner(myObj);

      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        System.out.println(data);
      }
      myReader.close();

    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");        
      e.printStackTrace();
    }         
  }
  }


class CheckingOut{
/* CheckingOut is when the user wants to check out a new book. It is called in the ViewAccount
* class when a user inputs '3'
 */
  public void GetBookTitle(String username){
  /*GetBookTitle takes the input for the title and uses LocalDate to create
   * a date object for todays date and the date 14 days/two weeks from today which
   * will be the return date. It then passes the info to writeToFile
   */

    Scanner read = new Scanner(System.in);
    System.out.println("Enter the title of the book you would like to checkout: ");
    String bookTitle = read.nextLine();
  
    LocalDate checkoutDate = LocalDate.now(); // Create a date object
    
    LocalDate returnDate =  LocalDate.now().plusDays(14); 
    
    writeToFile(checkoutDate, bookTitle, returnDate, username);
  
    read.close();

  }

  public void writeToFile(LocalDate checkoutDate, String BookTitle, LocalDate ReturnDate, String username){
  /*writeToFile writes the book title, todays date, and the returndate to the userr account */
    
    try{
      FileWriter fw = new FileWriter(username + ".txt", true);
      BufferedWriter bw = new BufferedWriter(fw);
      bw.newLine(); // Display the current date);
      bw.write("You checked out " + BookTitle + " on: " + checkoutDate);
      bw.newLine();
      bw.write("Your return date is: " + ReturnDate);      
      bw.close();
  }
  catch (IOException e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
  }
  }
}


class LoginOrSignUp{
/*LoginOrSignUp is the only function called in main. It either allows the user to 
 * login to a preexisting account or to create a new one
 */
    public static String getInput (){
  /*getInput takes and returns the user input it is called during readInput */
      Scanner read = new Scanner(System.in);
      System.out.println("Press \'1\' to Login \nPress \'2\' to sign up");
      String input = read.nextLine();
      return input;
    }
        
    public int readingInput() {
    /*readingInput directs the user to login if they select 1 and then calls
     * the login class. If the user inputs 2 it will redirect to the SignUp class
     * and if the user inputs anythin else it will loop until there is an acceptable input
     */

        String Uinput = getInput();

        if (Uinput.equals("1")){
          return 1;    //Let the user log in
        }

        else if (Uinput.equals("2")){
            return 2;
        }
        else {
          return 3;
        }
    }
    }
    

class SignUp {
/* SignUp takes a new login. It then stores the new
 * username and password and creates a new account (txt file) with the username
 * It returns the username to main
  */
    public String getNewLogin(){
    /* The user will create a new login, receive a thank you message and then asked
     * to officially login
    */
      Scanner read = new Scanner(System.in);
      System.out.println("Enter your new username: "); //new username
      String username = read.nextLine();

      System.out.println("Enter your new password: "); //new password
      String password = read.nextLine();

      storeUsername(username);
      storePassword(password);
      CreateFile(username);

      System.out.println("Thank you for creating an account with us\n");

      return username;
    }
    public void CreateFile(String username) {
      //CreateFile uses the new username to create an account
      try {
        File myObj = new File(username + ".txt");
        if (myObj.createNewFile()) {
          System.out.println("Account created for: " + username);
        } else {
          System.out.println("File already exists.");
        }
        FileWriter fw = new FileWriter(username + ".txt", true);
        BufferedWriter bw = new BufferedWriter(fw);    
        bw.write("Account Name: " + username);
        bw.newLine();
        bw.close();
        
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }
    public void storeUsername(String username){
    // store the username in the usernames.txt file
      try{
        FileWriter fw = new FileWriter("usernames.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(username);
        
        bw.close();
    }
    catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

  }

    public void storePassword(String password){
    // store the password in the passwords.txt file
      try{
        FileWriter fw = new FileWriter("passwords.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.newLine();
        bw.write(password);  
        bw.close();
    }
    catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  
  }
}
  

class Login {
/*The Login class is the class that passes the username into the username pointer class
 * Login also checks the entered username and password to see if they are in the files AND at
 * the same index to make sure they are a match
 */
    public String existingUser() {
        Scanner read = new Scanner (System.in);
        String user = "null";
        String pass = "null";


        //go to correct if there is something wrong it goes back to loginOrSignUp
        //I decided on this becuase someone may need to make a new account or selected wrong
        while (correct(user, pass) == false){
          System.out.println("Enter Username: "); //get the username
          user = read.nextLine();
          System.out.println("Enter Password: "); //get the password
          pass = read.nextLine(); 
          if(correct(user, pass) == false){
            System.out.println("Username or Password is incorrect please try again");
          }
        }


        System.out.println("Welcome " + user); //welcome message
        return user;

    }
    public boolean correct(String user, String password){ 
      /*correct checks the username and password to make sure they both exist and are at the same index */
      ReadFiles readfiles = new ReadFiles();
      ArrayList<String>usernameList = readfiles.Usernames();
      ArrayList<String>passwordList = readfiles.Passwords();

      if (passwordList.contains(password) && usernameList.contains(user)){
        int passIndex = passwordList.indexOf(password);
        int userIndex = usernameList.indexOf(user);
        if (passIndex == userIndex){
          return true;
         }
         else{
          return false;
        }

      }
      else{ 
        return false;
     }
    }
        
  }
  

class Username {
/* A setter getter class to pass username into other classes from the login class */
  private String username;
    public void setUsername(String user){
      this.username = user; 
    }
    public String getUsername(){
      return username;
    }
} 
    

class ReadFiles {
/* ReadFiles reads the usernames.txt file and the passwords.txt file and creates a list of
 * usernames and passwords to check in the Login class
 */
        public ArrayList<String> Usernames () {
          try {
            File myObj = new File("usernames.txt");
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> usernames = new ArrayList<String>();
  
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              usernames.add(data);
            }
            myReader.close();
            return (usernames);
  
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");        
            e.printStackTrace();
            ArrayList<String> usernames = new ArrayList<String>();
            return (usernames);
          }         
        }

        public ArrayList<String> Passwords () {
          try {
            File myObj = new File("passwords.txt");
            Scanner myReader = new Scanner(myObj);
            ArrayList<String> passwords = new ArrayList<String>();
  
            while (myReader.hasNextLine()) {
              String data = myReader.nextLine();
              passwords.add(data);
            }
            myReader.close();
            return (passwords);
  
          } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");        
            e.printStackTrace();
            ArrayList<String> passwords = new ArrayList<String>();
            return (passwords);
          }         
        }
      }
