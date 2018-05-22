package com.accountingmanager.Sys.Utils.JsonUtil;

import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class XCLog {
	private static XCLog gXcLog = null;
	private boolean isDebug = false;
	private FileOutputStream oStream = null;

	public void finalize() throws Throwable {
		if (this.oStream != null) {
			this.oStream.flush();
			this.oStream.close();
		}
		super.finalize();
	}

	public static void setLogMode(boolean flag, FileOutputStream stream) {
		if (gXcLog == null) {
			gXcLog = new XCLog();
		}
		gXcLog.isDebug = flag;
		gXcLog.oStream = stream;
	}

	public static void d(String tag, String msg) {
		if ((gXcLog != null) && (gXcLog.isDebug) && (tag != null)
				&& (msg != null)) {
			Log.d(tag, msg);
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (tag != null)
				&& (msg != null)) {
			try {
				gXcLog.oStream
						.write((XCToolkit.stringFromDate(new Date(),
								"yyyy-MM-dd HH:mm:ss")
								+ "  =====d "
								+ tag
								+ ": " + msg + "\n").getBytes());
			} catch (IOException e) {
				printStackTrace(e);
			}
		}
	}

	public static void i(String tag, String msg) {
		if ((gXcLog != null) && (gXcLog.isDebug) && (tag != null)
				&& (msg != null)) {
			Log.i(tag, msg);
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (tag != null)
				&& (msg != null)) {
			try {
				gXcLog.oStream
						.write((XCToolkit.stringFromDate(new Date(),
								"yyyy-MM-dd HH:mm:ss")
								+ "  -----i "
								+ tag
								+ ": " + msg + "\n").getBytes());
			} catch (IOException e) {
				printStackTrace(e);
			}
		}
	}

	public static void e(String tag, String msg) {
		if ((gXcLog != null) && (gXcLog.isDebug) && (tag != null)
				&& (msg != null)) {
			Log.e(tag, msg);
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (tag != null)
				&& (msg != null)) {
			try {
				gXcLog.oStream.write((XCToolkit.stringFromDate(new Date(),
						"yyyy-MM-dd HH:mm:ss")
						+ "  ******e "
						+ tag
						+ ": "
						+ msg + "\n").getBytes());
			} catch (IOException e) {
				printStackTrace(e);
			}
		}
	}

	public static void v(String tag, String msg) {
		if ((gXcLog != null) && (gXcLog.isDebug) && (tag != null)
				&& (msg != null)) {
			Log.v(tag, msg);
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (tag != null)
				&& (msg != null)) {
			try {
				gXcLog.oStream
						.write((XCToolkit.stringFromDate(new Date(),
								"yyyy-MM-dd HH:mm:ss")
								+ "  +++++v "
								+ tag
								+ ": " + msg + "\n").getBytes());
			} catch (IOException e) {
				printStackTrace(e);
			}
		}
	}

	public static void w(String tag, String msg) {
		if ((gXcLog != null) && (gXcLog.isDebug) && (tag != null)
				&& (msg != null)) {
			Log.w(tag, msg);
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (tag != null)
				&& (msg != null)) {
			try {
				gXcLog.oStream
						.write((XCToolkit.stringFromDate(new Date(),
								"yyyy-MM-dd HH:mm:ss")
								+ "  .....w "
								+ tag
								+ ": " + msg + "\n").getBytes());
			} catch (IOException e) {
				printStackTrace(e);
			}
		}
	}

	public static void printStackTrace(Throwable e) {
		if (e != null) {
			e.printStackTrace();
		}
		if ((gXcLog != null) && (gXcLog.oStream != null) && (e != null)) {
			try {
				StackTraceElement[] s = e.getStackTrace();
				StringBuffer buffer = new StringBuffer();
				if (e.getMessage() != null) {
					buffer.append(e.getMessage() + "\n");
				} else if (e.getCause() != null) {
					buffer.append(e.getCause().getMessage() + "\n");
				} else {
					buffer.append(e.getClass().getName() + "\n");
				}
				StackTraceElement[] arrayOfStackTraceElement1;
				int j = (arrayOfStackTraceElement1 = s).length;
				for (int i = 0; i < j; i++) {
					StackTraceElement em = arrayOfStackTraceElement1[i];
					buffer.append(em.toString() + "\n");
				}
				gXcLog.oStream.write((XCToolkit.stringFromDate(new Date(),
						"yyyy-MM-dd HH:mm:ss") + "  " + buffer + "\n")
						.getBytes());
			} catch (IOException ex) {
				printStackTrace(ex);
			}
		}
	}
}
