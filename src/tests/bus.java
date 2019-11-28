package tests;

import java.util.concurrent.TimeUnit;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyApplicationListener;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class bus {

	public static void main(String[] args) throws InterruptedException {
		Ivy I = new Ivy("SimonsBus", "Je suis tellement beau.", null);
		try {
			I.start("127.255.255.255:2010");
		} catch (IvyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		/*try {
			I.bindMsg("Palette:.*", new IvyMessageListener() {
				
				@Override
				public void receive(IvyClient client, String[] args) {
					System.out.println("Message palette");
					
				}
			});
		} catch (IvyException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		int colour = 0;
		while(true) {
			try {
				I.sendMsg("Palette:CreerRectangle x=10 y=10 longueur=50 hauteur=50 couleurFond="+colour+":0:0");
				TimeUnit.MILLISECONDS.sleep(10000);
				colour+=1%255;
			} catch (IvyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
