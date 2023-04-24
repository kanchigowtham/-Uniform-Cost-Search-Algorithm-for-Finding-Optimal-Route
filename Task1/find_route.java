/*
 * CSE 5360-003 Artificial Intelligence - I
 * Kanchi Gowtham Kumar
 * 1002044003
 */
import java.util.*;
import java.io.*;

public class find_route{

	private static String parseTextFile; 

	static int np = 0;
	static int gs = 1;

	static ArrayList<String> lstPath = new ArrayList<>();

	/*
	 * Method Name: addChildNodes
	 * Purpose: To add Child nodes to the parent nodes
	 */
 	public static void addChildNodes(node sourceCity,ArrayList<file> routeFound){ 
	
 		ArrayList<node> children=new ArrayList<node>(); 

 		for(file obj:routeFound){ 	
 			if(obj.getsource().equals(sourceCity.getName())){ // if city 1 is sourceCity then add all the cities and path to the node
 				node child=new node(obj.getdestination(),sourceCity.getDepth()+1,sourceCity.getCost()+obj.getpath(),sourceCity);
 				children.add(child);
 			}
 			else if(obj.getdestination().equals(sourceCity.getName())){ // if city 2 is sourceCity then add all the cities and path to the node
 				node child=new node(obj.getsource(),sourceCity.getDepth()+1,sourceCity.getCost()+obj.getpath(),sourceCity);
 				children.add(child);
 			}
 		}
 		sourceCity.setChildren(children);
	}

	/*
	 * Method Name: getpath
	 * Purpose: To get the path between two cities
	 */
	public static int getpath(String sourceCity,String destinationCity,ArrayList<file> routeFound){ 

    	int dist=0;
    	
    	for(int i=0;i<routeFound.size();i++){
    		if(sourceCity.equals(routeFound.get(i).source) || sourceCity.equals(routeFound.get(i).destination)){ 
    			if(destinationCity.equals(routeFound.get(i).destination) || destinationCity.equals(routeFound.get(i).source)){
    				dist=routeFound.get(i).path;
    			}
    		}
    	}
    	return dist;
	}

	/*
	 * Method Name: displayroute
	 * Purpose: To print the path from source city to the origin city
	 */
	public static void displayroute(node destinationCity,ArrayList<file> routeFound, int expandedCount){ 
	    	
    	ArrayList<String> parentNode= new ArrayList<String>();
    	
		double destprice = destinationCity.getCost();
		System.out.println("Nodes Popped: "+np);
		System.out.println("Nodes Expanded: "+expandedCount);
		System.out.println("Nodes Generated: "+gs);
    	System.out.println("path: "+destprice+ " km");
    	System.out.println("Route:");
		
		while(destinationCity.getParent()!=null){ // print the path

			double dist = getpath(destinationCity.getName(),destinationCity.getParent().getName(),routeFound);
			
			lstPath.add(destinationCity.getParent().getName()+" to "+ destinationCity.getName()+", "+ dist + " km");
			
			parentNode.add(destinationCity.getName());
			parentNode.add(destinationCity.getParent().getName());
			destinationCity=destinationCity.getParent();
		}   
		
		for(int i=lstPath.size()-1; i>=0; i--){
			System.out.println(lstPath.get(i));
		}
    }
 
 	public static void main(String[] args){	

		String fileName= args[0]; 
		String sourceCity=args[1]; //Source City
		String destinationCity=args[2]; //Destination city
		String arrRoute[]; 

		ArrayList<String> arrVisitedNodes = new ArrayList<String>();

		ArrayList<file> routeFound = new ArrayList<file>();

		node source=new node(sourceCity,0,0,null);

		try{

			BufferedReader br = new BufferedReader(new FileReader(fileName));
		
			while((parseTextFile = br.readLine()) != null){ //to get the distnace and routes from the txt file and saving it in the file object.

                if(parseTextFile.equals("END OF INPUT"))
                    break;

                arrRoute = parseTextFile.split(" ");
                
                file objFile= new file();
                objFile.source=arrRoute[0]; 
                objFile.destination=arrRoute[1];
                objFile.path=Integer.parseInt(arrRoute[2]);
                routeFound.add(objFile); 
			}
            br.close();

		}catch(FileNotFoundException excep){
            System.out.println("Could not find the file");                
        }catch(IOException excep){
            System.out.println("Cannot read the file");                  
        }


		//Priority queue to perform uniform cost search
        PriorityQueue<node> fringe = new PriorityQueue<node>(1000, new Comparator<node>(){ 
    
            public int compare(node node1, node node2){
                
                if(node1.getCost()>node2.getCost()){
                	return 1;
                }
                if(node1.getCost()==node2.getCost()){
	                return 0;
	            }

                return -1;
            }
        }
        );			    

	   fringe.add(source); 

	   while(!fringe.isEmpty()){

			np++; 

			node temp=fringe.poll(); //To get the first item from the node 

	   		if(temp.getName().equals(destinationCity)){
	   			displayroute(temp, routeFound, arrVisitedNodes.size());
	   			break;
	   		}

			if(!arrVisitedNodes.contains(temp.getName())){ //Adding to the fringe if the node is not visited 
	
				arrVisitedNodes.add(temp.getName());  

				addChildNodes(temp,routeFound);
			    int k=0;
				while(k<temp.getChildren().size())
				{
					gs++;
					node child= temp.getChildren().get(k);
					fringe.add(child);
					k++;
				}


				
		    }
	   
	   		if(fringe.isEmpty()){

				System.out.println("Nodes Popped: "+np);
				System.out.println("Nodes Expanded: "+ arrVisitedNodes.size());
				System.out.println("Nodes Generated: "+gs);
	   			System.out.println("path= infinity");
	   			System.out.println("Route:"+'\n'+"None");

	   		}
		}
	}
}