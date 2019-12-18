import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

import java.util.Timer;
import java.util.TimerTask;

public class CreateObject {
	static int timeOut = 3;
	static String state = "wm";
	static Object obj = null;
	//temporary position
	static int x=0;
	static int y=0;
	final static Timer t1 = new Timer();
	static boolean timeout = false;
	static Ivy I = new Ivy("MoteurFusion", "MoteurFusion en ligne", null);
	
	public static void main(String[] args) {

		
		IvyMessageListener gesturelistener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				System.out.println("seen "+args[0]);
				String message = args[0];
				System.out.println("message was: "+message);
				//gesture message
				switch (state) {
				case "wm":
					switch (message) {
					case "Ellipse":
						state = "CR/CE";
						obj = new Object(message);
						t1.schedule(new TimerTask() {
					        int n = 0;
					        @Override
					        public void run() {
					            if (++n == timeOut) {
					                t1.cancel();
					                t1timeout();
					            }
					        }
					    },1000,1000);
						break;
					case "Triangle":
						state = "CR/CE";
						obj = new Object(message);
						break;

					default:
						break;
					}
				default: 
					break;
				}
			}
		};
		IvyMessageListener speechlistener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				System.out.println("heard "+args[0]);
				if(args[0].equalsIgnoreCase("blue") || args[0].equalsIgnoreCase("yellow") || 
						args[0].equalsIgnoreCase("green") || args[0].equalsIgnoreCase("red") || 
						args[0].equalsIgnoreCase("white") || args[0].equalsIgnoreCase("black") || args[0].equalsIgnoreCase("gray")) {
					switch(state) {
					case "CR/CE":
						obj.setColour(args[0]);
						state = "CR/CE";
						t1.cancel();
						t1.schedule(new TimerTask() {
					        int n = 0;
					        @Override
					        public void run() {
					            if (++n == timeOut) {
					                t1.cancel();
					                t1timeout();
					            }
					        }
					    },1000,1000);
						break;
					}
					
				}
				if(args[0].equalsIgnoreCase("here")) {
					switch(state) {
					case "C":
						state = "CR/CE";
						obj.setPosX(x);
						obj.setPosY(y);
						t1.cancel();
						t1.schedule(new TimerTask() {
					        int n = 0;
					        @Override
					        public void run() {
					            if (++n == timeOut) {
					                t1.cancel();
					                t1timeout();
					            }
					        }
					    },1000,1000);
						break;
					}
				
				}
				if(args[0].equalsIgnoreCase("here")) {
					
				}
			}
				
		};
		IvyMessageListener clicklistener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				int x = Integer.parseInt(args[0]);
				int y = Integer.parseInt(args[1]);
				System.out.println("felt a click at " +x+","+y);	
			}
		};
		try {
			I.bindMsg("OneDollar Reco=(.*)", gesturelistener );
			I.bindMsg("sra5 Parsed=(.[a-z]*) ", speechlistener);
			I.bindMsg("Palette:MouseClicked x=(.[0123456789]*) y=(.[a-z0123456789=]*)", clicklistener);
			System.out.println("Listening... ");
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
	private static void t1timeout() {
		System.out.println("no object will be created. u suck");
		
	}
}
