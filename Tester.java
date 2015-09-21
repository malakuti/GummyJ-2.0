package org.gummymodules.test;

import org.gummymodules.core.types.EventType;
import org.gummymodules.core.metamodel.GummyModule;
import org.gummymodules.core.runtime.GummyJ;
import org.gummymodules.generated.file.*;


public class Tester {
	
	private static String userID = "foo";
	private static String fileID = "bar";
	
	private static void message(String type, String content) {
		System.out.println(">>>" + " " + ("[" + type + "]") + " " + content);
	}
	
	private static void sleep(long milliseconds) {
		try {
			Thread.sleep(milliseconds);
		}
		catch (Exception exception) {
			message("warn", "could not wait");
		}
	}
	
	private static EventType create(String name) {
		EventType event;
		switch (name) {
			case "begin": {event = new BeginEvent(); break;}
			case "end": {event = new EndEvent(); break;}
			case "open": {event = new OpenEvent(); break;}
			case "close": {event = new CloseEvent(); break;}
			case "read": {event = new ReadEvent(); break;}
			case "write": {event = new WriteEvent(); break;}
			default: {event = null; break;}
		}
		if (event != null) {
			event.set("userID", userID);
			event.set("fileID", fileID);
			event.set("publisher", userID);
			event.set("userrole", "premium");
		}
		return event;
	}
	
	private static void send_single(String name) {
		EventType event = create(name);
		if (event != null) {
			try {
				message("info", "sending '" + event.toString() + "'");
				GummyJ.publish(event);
			}
			catch (Exception exception) {
				message("warn", "error while processing event");
			}
		}
		else {
			message("warn", "invalid event name '" + name + "'");
		}
	}
	
	private static void send_sequence(long pause, String[] names) {
		for (String name : names) {
			send_single(name);
			sleep(pause);
		}
	}
	
	private static void test(String usecase) {
		message("info", "running usecase '" + usecase + "'");
		switch (usecase) {
			case "regular": {
				send_sequence(1000, new String[] {"begin","open","read","write","close","end"});
				break;
			}
			case "wrongorder": {
				send_sequence(1000, new String[] {"begin","open","read","close","write","end"});
				break;
			}
			case "timeout": {
				send_single("begin");
				sleep(1000);
				for (int iteration = 0; iteration < 4; iteration += 1) {
					send_single("open");
					sleep(3000);
					send_single("close");
					sleep(1000);
				}
				send_single("end");
				sleep(1000);
				break;
			}
			default: {
				message("warn", "invalid usecase '" + usecase + "'");
				break;
			}
		}
	}
	
	private static void init() {
		GummyJ.setHandler(
			new GummyJ.Handler() {
				public void handle(EventType event) {
					message("info", "receiving '" + event.toString() + "'");
				}
			}
		);
		try {
			GummyJ.introduce(PremiumUsersEPAs.class, "index", userID, fileID);
		}
		catch (Exception exception) {
			message("warn", "could not introduce module");
		}
		
	}
	
	private static void terminate() {
		GummyJ.terminate();
	}
	
	public static void main(String[] arguments) {
		init();
		if (arguments.length == 0) message("warn", "no usecase specified");
		for (String usecase : arguments) {
			test(usecase);
		}
		terminate();
	}
	
}

