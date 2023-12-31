import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * The main user interface for the Blockchain program.
 * 
 * @author Chloe Kelly
 * @author Lydia Ye
 */
public class BlockChainDriver {
  public static void main(String[] args) throws NoSuchAlgorithmException {
    //Make sure the command argument is valid
    if (args.length != 1){
      System.err.println("Invalid number of command line arguments.");
      System.exit(1);
    } // if
    int initAmount = 0;
    try{
      initAmount = Integer.valueOf(args[0]);
    } catch (Exception e) {
      System.err.println("Please enter a valid integer for the starting amount.");
      System.exit(1);
    } // try/catch

    //Create Blockchain
    BlockChain chain = new BlockChain(initAmount);

    //Create scanners and printwriters
    Scanner input = new Scanner(System.in);
    PrintWriter pen = new PrintWriter(System.out, true);
    String command = "";

    while (!command.equals("quit")) {
      //Print chain
      pen.println(chain.toString());
      //Prompts
      pen.println("Please enter your command: ");
      command = input.nextLine();

      switch (command) {
        case "mine":
          pen.println("Amount transferred?");
          int amountMine = Integer.valueOf(input.nextLine());
          //Create block
          Block tempMine = chain.mine(amountMine);
          pen.println("Amount = " + amountMine + ", nonce = " + tempMine.getNonce());
          break;

        case "append":
          pen.println("Amount transferred?");
          int amountAppend = Integer.valueOf(input.nextLine());
          pen.println("Nonce?");
          long nonce = Long.valueOf(input.nextLine());
          //Create block
          Block tempAppend = new Block(chain.getSize(), amountAppend, chain.getHash(), nonce);
          try {
            chain.append(tempAppend);
          } catch (Exception e) {
            pen.println("Invalid block.");
          } // try/catch
          break;

        case "remove":
          chain.removeLast();
          break;

        case "check":
          if(chain.isValidBlockChain()){
            pen.println("Chain is valid!");
          } else {
            pen.println("Chain is not valid!");
          } // if/else
          break;

        case "report":
          chain.printBalances();
          break;

        case "help":
          pen.print("Valid commands:\n" +
                    "\tmine: discovers the nonce for a given transaction\n" +
                    "\tappend: appends a new block onto the end of the chain\n" +
                    "\tremove: removes the last block from the end of the chain\n" +
                    "\tcheck: checks that the block chain is valid\n" +
                    "\treport: reports the balances of Alexis and Blake\n" +
                    "\thelp: prints this list of commands\n" +
                    "\tquit: quits the program\n");
          break;

        case "quit":
          break;

        default:
          pen.println("Invalid command. Type \"help\" for a list of commands.");
      } // switch
      pen.println();
    } // while

    if (input != null){
      input.close();
    } // if
  } // main(String[])
} // class BlockChainDriver()
