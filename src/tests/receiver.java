package tests;

import java.util.concurrent.TimeUnit;

import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

public class receiver {

	public static void main(String[] args) {
		Ivy I = new Ivy("SimonsReceiver", "Salut, ca va?", null);
		IvyMessageListener listener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				System.out.println("ta gueule");
			}
		};
		try {
			I.bindMsg("OneDollar Reco=Move*", listener );
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
