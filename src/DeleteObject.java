import java.util.ArrayList;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;


public class DeleteObject {
	
	public static void main(String[] args)
	{
		Ivy I = new Ivy("SimonsReceiver", "Salut, ca va?", null);
		IvyMessageListener listener = new IvyMessageListener() {
						
			ArrayList<String> coordonne = new ArrayList<>();
			String emp = "";
			String obj = "";
			String nom = ""; 
			String color = ""; 
			int x = 0; 
			int y = 0; 
			@Override
			public void receive(IvyClient client, String[] args) {
				// TODO Auto-generated method stub
				String 	Etat = args[0];
				
				try {
					I.sendMsg("Palette:CreerRectangle x="+262+" y="+101);
				} catch (IvyException e1) {
					e1.printStackTrace();
				}
								
				if(Etat.equals("Supprimer")) {
					System.out.println(" Action " + Etat);
					I.unBindMsg("OneDollar Reco=Supprimer");

					try {
						I.bindMsg("Palette:MouseClicked(.*)", this);						
						I.bindMsg("sra5 Parsed=(.[a-z]*) ", this);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
												
				if(Etat.equals("here") || Etat.equals("thisobject")) {
					emp = Etat;
					System.out.println("Emplacement " + emp);
				}
																			
				if(Etat.length() > 10) {				
					try 
					{					
						I.bindMsg("Palette:ResultatTesterPoint(.*)", this);
					}
					catch (IvyException e) {
						e.printStackTrace();
					}
					
					String[] tabXY = Etat.split(" ");		
					System.out.println(Etat);
					if(tabXY.length > 2)
					{
						x  =  Integer.parseInt(tabXY[1].split("=")[1]);
						y  =  Integer.parseInt(tabXY[2].split("=")[1]);
						nom = tabXY[3].split("=")[1];
					}
									
					System.out.println("Coordonée : " + x +" et " + y + "  -- " + nom);
				}
				
				if(x != 0 && y != 0 && !nom.equals(""))
				{
					try 
					{					
						I.bindMsg("Palette:DemanderInfo nom=" + nom, this);
					}
					catch (IvyException e) {
						e.printStackTrace();
					}
					
					String[] tabInfo = Etat.split(" ");
					if(tabInfo.length > 5)
					{
						color = tabInfo[6].split("=")[1];
					}
					
				}
																			
				
				if(!emp.equals("") && !obj.equals("") && !nom.equals("")) {
					System.out.println("Emplacement FINALE DE L'OBJET :" + emp +" " + obj);
															
					try {
						I.sendMsg("Palette:SupprimerObjet nom=" + nom + " x="+x+" y="+y);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
					coordonne.clear();
					I.unBindMsg("Palette:MouseClicked(.*)");
					
				}
																
			}
		};
	
		try {
			I.bindMsg("OneDollar Reco=(.*)", listener );

			System.out.println("I listen to ");
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			I.start("127.255.255.255:2010");
		} catch (IvyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
