
import java.util.ArrayList;
import java.util.HashMap;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class DeplaceObject {

	public DeplaceObject() {
		// TODO Auto-generated constructor stub
	}


	/*
	 * on spécifie l'emplacement par la voix et l'objet à déplacer, ensuite on clique sur l'objet et l'emplacement 
	 * on le faire par ordre : clic sur objet puis dire cet objet -> click sur l'emplacement et dire ici 
	 * une fois que l'objet, l'emplacement sont spécifier par la voix et le click, l'objet est déplacer
	 * */
	public static void main(String[] args)
	{

		Ivy I = new Ivy("SimonsReceiver", "Salut, ca va?", null);
		IvyMessageListener listener = new IvyMessageListener() {

			ArrayList<String> coordonne = new ArrayList<>();
			String emp = "";
			String obj = "";
			@Override
			public void receive(IvyClient client, String[] args) {
				String 	Etat = args[0];
				
				if(Etat.equals("Move")) {
					System.out.println(" Action " + Etat);
					I.unBindMsg("OneDollar Reco=Move");

					try {
						I.bindMsg("Palette:MouseClicked(.*)", this);
						I.bindMsg("Palette:ResultatTesterPoint(.*)", this);
						I.bindMsg("sra5 Parsed=(.[a-z]*) ", this);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}


				}
				//**********permet de creer un rectangle pour tester le deplacement**************************************
				//																try {
				//																	I.sendMsg("Palette:CreerRectangle x="+262+" y="+101);
				//																} catch (IvyException e1) {
				//																	// TODO Auto-generated catch block
				//																	e1.printStackTrace();
				//																}

				if(Etat.length() > 10) {
					String[] tabXY = Etat.split(" ");
					//System.out.println(tabXY.length + " " +  tabXY[1] + " " +  " " + tabXY[2]);
					int x  =  Integer.parseInt(tabXY[1].split("=")[1]);
					int y  =  Integer.parseInt(tabXY[2].split("=")[1]);

					try {
						I.sendMsg("Palette:TesterPoint x="+x+" y="+y);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

				if(Etat.contains("nom=")  ) {
					coordonne.add("OBJ" + Etat);
					for (int j = 0; j < coordonne.size(); j++) {
						if(coordonne.get(j).contains("OBJ") )
							coordonne.remove(j-1);
					}				
					I.unBindMsg("Palette:ResultatTesterPoint(.*)");
				}

				if(Etat.equals("thisobject")) {
					obj = Etat;
				}



				System.out.println(Etat.contains("Palette:ResultatTesterPoint") + " -- " + Etat );
				if( Etat.contains("x") && !Etat.contains("nom") ) {
					coordonne.add("POS" + Etat);
				}

				if(Etat.equals("here")) {
					emp = Etat;
					System.out.println("Emplacement " + emp);
				}
				System.out.println(coordonne + " FINALLY" );

				String nom = "";
				int xDep = 0;
				int yDep = 0 ;
				String[] tabDep = null;
				for(String deplacement : coordonne) {


					if(deplacement.contains("OBJ"))
						nom = deplacement.split(" ")[3].split("=")[1];
					if (deplacement.contains("POS")) {
						System.out.println(coordonne + " " + coordonne.contains("OBJ"+Etat) + " *** " + "OBJ"+Etat);
						tabDep = deplacement.split(" ");
						//	System.out.println(tabDep.length + " " +  tabDep[1] + " " +  " " + tabDep[2]);
						//System.out.println(tabXY[0].split("=")[0]+ " " + tabXY[0].split("=").length  )

					}
				}


				if(!nom.equals("") && tabDep != null && !emp.equals("") && !obj.equals("")) {
					System.out.println("Emplacement " + emp +" " + obj);
					xDep  =  Integer.parseInt(tabDep[1].split("=")[1]);
					yDep  =  Integer.parseInt(tabDep[2].split("=")[1]);
					//	System.out.println("sdssdsd"  + objetADeplacer + " " + nom);

					System.out.println(nom + " " + " x="+xDep+" y="+yDep) ;
					try {
						I.sendMsg("Palette:DeplacerObjetAbsolu nom=" + nom + " x="+xDep+" y="+yDep);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					//					System.out.println("binding again");
					//					try {
					//						I.bindMsg("Palette:ResultatTesterPoint(.*)", this);
					//					} catch (IvyException e) {
					//						// TODO Auto-generated catch block
					//						e.printStackTrace();
					//					}
					coordonne.clear();
					I.unBindMsg("Palette:MouseClicked(.*)");
					I.unBindMsg("Palette:ResultatTesterPoint(.*");
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
