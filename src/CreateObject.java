import fr.dgac.ivy.Ivy;
import fr.dgac.ivy.IvyClient;
import fr.dgac.ivy.IvyException;
import fr.dgac.ivy.IvyMessageListener;

import java.util.Timer;
import java.util.TimerTask;

public class CreateObject {
	static int timeOut = 3;
	static String state = "wm";
	static FusionObject obj = null;
	//temporary position
	static int x=0;
	static int y=0;
	static Timer t1;
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
						if(message.equals("Ellipse") || message.equals("Rectangle")) {
							state = "CR/CE";
							System.out.println("create new object... ");
							obj = new FusionObject(message);
							t1 = new Timer();
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
						}
							break;
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
						t1 = new Timer();
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
					default: 
						break;
					}
					
				}
				if(args[0].equalsIgnoreCase("here")) {
					switch(state) {
					case "C":
						System.out.println("Setting temp position....");
						state = "CR/CE";
						obj.setPosX(x);
						obj.setPosY(y);
						t1.cancel();
						t1 = new Timer();
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
				if(args[0].equalsIgnoreCase("thiscolor")) {
					System.out.println("entendu de cette couleur, etat egal: "+state);
					switch(state) {
					case "CR/CE":
						state = "WC";
						System.out.println("now waiting for click");
						t1.cancel();
						t1 = new Timer();
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
					case "C":
						state = "WGO";
						System.out.println("now waiting for palette to return object....");
						try {
							I.sendMsg("Palette:TesterPoint x="+x+" y="+y);
						} catch (IvyException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						t1.cancel();
						t1 = new Timer();
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
					}
				}
			}
				
		};
		IvyMessageListener clicklistener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				switch(state) {
				case "CR/CE":
					state = "C";
					x = Integer.parseInt(args[0]);
					y = Integer.parseInt(args[1]);
					System.out.println("felt a click at " +x+","+y);
					t1.cancel();
					t1 = new Timer();
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
				case "WC":
					state = "WGO";
					x = Integer.parseInt(args[0]);
					y = Integer.parseInt(args[1]);
					System.out.println("WC felt a click at " +x+","+y+" checking point...");	
					System.out.println("now waiting for palette to return object....");
					try {
						I.sendMsg("Palette:TesterPoint x="+x+" y="+y);
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t1.cancel();
					t1 = new Timer();
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
		};
		IvyMessageListener objectidlistener = new IvyMessageListener() {
			
			@Override
			public void receive(IvyClient client, String[] args) {
				String id = args[2];
				System.out.println("it s the id of object "+id);
				switch(state) {
				case "WGO":
					state = "WGC";
					//smpGCO    send msg “palette get colour of object <name>
					try {
						I.sendMsg("Palette:DemanderInfo nom="+id);
						System.out.println("message for information retrieval sent.");
					} catch (IvyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					t1.cancel();
					t1 = new Timer();
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
				default: 
					break;
				}
			}
		};
		IvyMessageListener infolistener = new IvyMessageListener() {
			@Override
			public void receive(IvyClient client, String[] args) {
				//arg0: nom, arg1: x, arg2: y, arg3: longeuer, arg4: hauteur, arg5: couleurFond, arg6: couleurContour
				// TODO Auto-generated method stub
				switch(state) {
				case "WGC":
					state = "CR/CE";
					//sCO:        enregistrer la couleur dans l’objet créé
					System.out.println("saving colour "+args[5] +" to object");
					obj.colour = args[5];
					t1.cancel();
					t1 = new Timer();
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
				default: 
					break;
				}
				
			}
		};
		
		try {
			I.bindMsg("OneDollar Reco=(.*)", gesturelistener );
			I.bindMsg("sra5 Parsed=(.[a-z]*) ", speechlistener);
			I.bindMsg("Palette:MouseClicked x=(.[0123456789]*) y=(.[0123456789]*)", clicklistener);
			I.bindMsg("Palette:ResultatTesterPoint x=(.[0123456789]*) y=(.[0123456789]*) nom=(.*)", objectidlistener);
			I.bindMsg("Palette:Info nom=(.[a-zA-Z0123456789]*) x=(.[0123456789]*) y=(.[0123456789]*) longueur=(.[0123456789]*) hauteur=(.[0123456789]*) couleurFond=(.[a-z]*) couleurContour=(.[a-z]*)", infolistener);
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
		System.out.println("TIME OUT!");
		if(state!="wm") {
			state = "wm";
			t1.cancel();
			//create current object:
			try {
				if(obj.type.equals("Ellipse")) {
					System.out.println("Creating Ellipse");
					I.sendMsg("Palette:CreerEllipse x="+obj.getPosX()+" y="+obj.getPosY()+" couleurFond="+obj.getColour());
				}
				if(obj.type.equals("Rectangle")) {
					System.out.println("Creating Rectangle");
					I.sendMsg("Palette:CreerRectangle x="+obj.getPosX()+" y="+obj.getPosY()+" couleurFond="+obj.getColour());
				}
			} catch (IvyException e1){
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
			System.out.println("no object will be created.");
		
	}
}
