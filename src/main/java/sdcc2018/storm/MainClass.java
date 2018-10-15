package sdcc2018.storm;

import sdcc2018.storm.control.TopologyGreeenDuration;
import sdcc2018.storm.query1.Topology1;
import sdcc2018.storm.query2.Topology2;
import sdcc2018.storm.stateTrafficLight.TopologyStateTrafficLight;

public class MainClass {
    public static void main(String args[])throws Exception{
        if (args != null && args.length > 0) {
            System.out.println("argument1=" + args[0]);
            if(args[0].equals("1")){
                new Topology1().runMain(args);
            }
            else if(args[0].equals("2")){
                new Topology2().runMain(args);
            }
            else if(args[0].equals("3")){
                new TopologyGreeenDuration().runMain(args);
            }
            else if(args[0].equals("4")){
                new TopologyStateTrafficLight().runMain(args);
            }
            else{
                System.out.println("number topology not allowed");
                System.out.println("number topolgy allowed are 1,2,3 or 4");
                System.exit(0);
            }
        }
        else{
            System.out.println("insert one argument <numberTopology>");
            System.out.println("number topolgy allowed are 1,2,3 or 4");
            System.exit(0);
        }
    }
}
