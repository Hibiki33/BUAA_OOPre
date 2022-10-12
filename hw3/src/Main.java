import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);
        ArrayList<Branch> branches = new ArrayList<>();
        Branch presentBranch = new Branch("1", new ArrayList<>(), new ArrayList<>());
        branches.add(presentBranch);
        int operateNumbers = reader.nextInt();
        for (int i = 0; i < operateNumbers; i++) {
            int orderType = reader.nextInt();
            if (orderType <= 10) {
                presentBranch.orders(orderType, reader);
            } else {
                String branchName = reader.next();
                if (orderType == 11) {
                    Branch newBranch = presentBranch.branchClone(branchName);
                    branches.add(newBranch);
                    presentBranch = newBranch;
                } else {
                    for (Branch x : branches) {
                        if (x.getName().equals(branchName)) {
                            presentBranch = x;
                            break;
                        }
                    }
                }
            }
        }
        reader.close();
    }
}
