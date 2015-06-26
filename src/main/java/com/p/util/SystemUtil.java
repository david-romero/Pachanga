/**
 * 
 */
package com.p.util;

/**
 * @author David
 *
 */
public class SystemUtil {

	private static final String OS_IPHONE = "IPhone";
	private static final String OS_ANDROID = "Android";
	private static final String OS_UNIX = "Unix";
	private static final String OS_MAC = "Mac";
	private static final String OS_WINDOWS = "Windows";
	private static final String IPHONE = "iphone";
	private static final String ANDROID = "android";
	private static final String UNIX = "x11";
	private static final String MAC = "mac";
	private static final String WINDOWS = "windows";
	
	/**
	 * @param userAgent
	 * @param user
	 * @param browser
	 * @return
	 */
	public static String getBrowser(String userAgent, String user) {
		String browser = "";
		// ===============Browser===========================
		if (user.contains("msie")) {
			String substring = userAgent.substring(userAgent.indexOf("MSIE"))
					.split(";")[0];
			browser = substring.split(" ")[0].replace("MSIE", "IE") + "-"
					+ substring.split(" ")[1];
		} else if (user.contains("safari") && user.contains("version")) {
			browser = (userAgent.substring(userAgent.indexOf("Safari")).split(
					" ")[0]).split("/")[0]
					+ "-"
					+ (userAgent.substring(userAgent.indexOf("Version")).split(
							" ")[0]).split("/")[1];
		} else if (user.contains("opr") || user.contains("opera")) {
			if (user.contains("opera"))
				browser = (userAgent.substring(userAgent.indexOf("Opera"))
						.split(" ")[0]).split("/")[0]
						+ "-"
						+ (userAgent.substring(userAgent.indexOf("Version"))
								.split(" ")[0]).split("/")[1];
			else if (user.contains("opr"))
				browser = ((userAgent.substring(userAgent.indexOf("OPR"))
						.split(" ")[0]).replace("/", "-")).replace("OPR",
						"Opera");
		} else if (user.contains("chrome")) {
			browser = (userAgent.substring(userAgent.indexOf("Chrome")).split(
					" ")[0]).replace("/", "-");
		} else if ((user.indexOf("mozilla/7.0") > -1)
				|| (user.indexOf("netscape6") != -1)
				|| (user.indexOf("mozilla/4.7") != -1)
				|| (user.indexOf("mozilla/4.78") != -1)
				|| (user.indexOf("mozilla/4.08") != -1)
				|| (user.indexOf("mozilla/3") != -1)) {
			browser = "Netscape-?";

		} else if (user.contains("firefox")) {
			browser = (userAgent.substring(userAgent.indexOf("Firefox")).split(
					" ")[0]).replace("/", "-");
		} else if (user.contains("rv")) {
			browser = "IE";
		} else {
			browser = "UnKnown, More-Info: " + userAgent;
		}
		return browser;
	}

	/**
	 * @param userAgent
	 * @return
	 */
	public static String getOs(String userAgent) {
		String os;
		// =================OS=======================
		if (userAgent.toLowerCase().indexOf(WINDOWS) >= 0) {
			os = OS_WINDOWS;
		} else if (userAgent.toLowerCase().indexOf(IPHONE) >= 0) {
			os = OS_IPHONE;
		} else if (userAgent.toLowerCase().indexOf(MAC) >= 0) {
			os = OS_MAC;
		} else if (userAgent.toLowerCase().indexOf(UNIX) >= 0) {
			os = OS_UNIX;
		} else if (userAgent.toLowerCase().indexOf(ANDROID) >= 0) {
			os = OS_ANDROID;
		} else {
			os = "UnKnown, More-Info: " + userAgent;
		}
		return os;
	}
	
}
