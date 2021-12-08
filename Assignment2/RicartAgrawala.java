// To implement Ricart Agrawala Symmetric Algorithm
import java.util.Scanner;

class RicartAgrawala
{
    int no_of_nodes, timestamp[][];
    // timestamp is a matrix of size 2xN where the ith column of the second row is the node having a timestamp stored in the ith column of the first row
    // 0 values in the first row of the timestamp indicates that the corresponding node is not interested in entering into the critical section

    public RicartAgrawala(int n)
    {
        no_of_nodes=n;
        timestamp=new int [2][no_of_nodes];
    }

    public void getRequest()
    {
        Scanner scanner=new Scanner(System.in);
        int i,val;
        char ans;
        for(i=0;i<no_of_nodes;i++)
        {
            // Iput should either be Y if the respective node wants to enter into critical section or N if the repective node does not want to enter into critical scetion
            //ans='';
            System.out.print("\n Does the node "+(i+1)+" want to enter into Critical Section (Y/N) ? ");
            ans=scanner.next().charAt(0);
            //scanner.nextLine();
            if(ans=='y' || ans=='Y')
            {
                System.out.print("\nEnter the timestamp of the node"+(i+1)+" :");
                val=scanner.nextInt();
                if(val<1)
                {
                    System.out.println("Invalid Input!");
                    scanner.close();
                    System.exit(0);
                }
                timestamp[0][i]=val;
                timestamp[1][i]=i+1;
            }
            else if(ans=='n' || ans=='N')
                timestamp[1][i]=i+1;
            else
            {
                System.out.println("Invalid Input!");
                scanner.close();
                System.exit(0);
            }
        }
        scanner.close();
    }

    public void giveResponse()
    {
        int i,j,temp1,temp2;
        
        for (i = 0; i < no_of_nodes-1; i++)
        {
            for (j = 0; j < no_of_nodes-i-1; j++)
            {
                if (timestamp[0][j] > timestamp[0][j+1])
                {
                    // The matrix timestamp is sorted with respect to the first row.
                    temp1 = timestamp[0][j];
                    temp2 = timestamp[1][j];
                    timestamp[0][j] = timestamp[0][j+1];
                    timestamp[1][j] = timestamp[1][j+1];
                    timestamp[0][j+1] = temp1;
                    timestamp[1][j+1] = temp2;
                }
            }
        }

        i=0;
        while(i<no_of_nodes)
        {
            // To check whether the node wants to enter into Critical Section or not
            if(timestamp[0][i]==0)
            {
                i++;
                if(i==no_of_nodes-1) 
                    System.out.print("No nodes are waiting to enter into Critical Section");
            }
            else
            {
                System.out.println("\nThe node"+timestamp[1][i]+" enters into the Critical Section having a timestamp of "+timestamp[0][i]);
                System.out.print("The list of node/nodes which is/are waiting to enter into Critical Section are: ");
                //The last node in the queue has entered into Critical Section, hence no node are waiting to enter into CS
                if(i==no_of_nodes-1)
                     System.out.print("None");
                else
                {
                    for(j=i+1;j<no_of_nodes;j++)
                    {
                        System.out.print("Node"+timestamp[1][j]+"\t");
                    }
                }
                System.out.println("\nThe node"+timestamp[1][i]+" comes out of the Critical Section");
                timestamp[0][i]=0;
                i++;
            }
        }        
    }

    public static void main(String args[])
    {
        Scanner scanner=new Scanner(System.in);
        int n;   
        System.out.print("\nEnter the number of nodes : ");
        n=scanner.nextInt();
        if(n<2)
        {
            System.out.println("Invalid Input!");
            scanner.close();
            return;
        }
       
        RicartAgrawala obj = new RicartAgrawala(n);

        obj.getRequest();

        obj.giveResponse();
        scanner.close();
    }
}