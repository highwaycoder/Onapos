package onapos;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Class to provide various static fields and methods for the rest of the
 * project
 * 
 * @author Chris Browne <yoda2031@gmail.com>
 * 
 */
public class Onapos {
	public static DateFormat SDF = new SimpleDateFormat("dd/MM/yyyy");
	public static boolean DEBUG_MODE = java.lang.management.ManagementFactory
			.getRuntimeMXBean().getInputArguments().toString()
			.contains("-agentlib:jdwp");
}
