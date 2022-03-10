package cn.com.lgf.common.debug;

import android.util.Log;

public class DebugLog {
    public static boolean isDebug = DebugStatus.isDev() || DebugStatus.isTest();
    public static boolean needLog = true;
    private static volatile String commonLogTag = "CommonLog";
    private static int LOG_MAX_LENGTH = 2000;

    public static void setTag(String tag) {
        commonLogTag = tag;
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param msg The message you would like logged.
     */
    public static void v(String tag, String msg) {
        if (isDebug || needLog) {
            Log.v(commonLogTag + ":" + tag, buildMessage(msg));
        }
    }

    public static void v(String msg) {
        if (isDebug || needLog) {
            Log.v(commonLogTag, buildMessage(msg));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void v(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.v(commonLogTag + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void v(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.v(commonLogTag, buildMessage(msg), throwable);
        }
    }

    public static void e(String msg) {
        if (isDebug || needLog) {
            Log.e(commonLogTag, buildMessage(msg));
        }
    }

    /**
     * Send a DEBGU log message.
     *
     * @param msg The message you would like logged.
     */
    public static void d(String tag, String msg) {
        if (isDebug || needLog) {
            Log.d(commonLogTag + ":" + tag, buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (isDebug || needLog) {
            Log.d(commonLogTag, buildMessage(msg));
        }
    }

    /**
     * Send a DEBGU log message and log the exception.
     *
     * @param msg       The message you would like logge,d.
     * @param throwable An exception to log
     */
    public static void d(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.d(commonLogTag + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void d(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.d(commonLogTag, buildMessage(msg), throwable);
        }
    }

    /**
     * Send a INFO log message.
     *
     * @param msg The message you would like logged.
     */
    public static void i(String tag, String msg) {
        if (isDebug || needLog) {
            int strLength = msg.length();
            int start = 0;
            int end = LOG_MAX_LENGTH;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.i(commonLogTag + ":" + tag + i, buildMessage(msg.substring(start, end)));
                    start = end;
                    end = end + LOG_MAX_LENGTH;
                } else {
                    Log.i(commonLogTag + ":" + tag + i, buildMessage(msg.substring(start, strLength)));
                    break;
                }
            }
        }
    }

    public static void i(String msg) {
        if (isDebug || needLog) {
            Log.i(commonLogTag + ":", buildMessage(msg));
        }
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void i(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.i(commonLogTag + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void i(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.i(commonLogTag, buildMessage(msg), throwable);
        }
    }

    /**
     * Send a WARN log message.
     *
     * @param msg The message you would like logged.
     */
    public static void w(String tag, String msg) {
        if (isDebug || needLog) {
            Log.w(commonLogTag + ":" + tag, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (isDebug || needLog) {
            Log.w(commonLogTag, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void w(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.w(commonLogTag + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void w(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.w(commonLogTag, buildMessage(msg), throwable);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg The message you would like logged.
     */
    public static void e(String tag, String msg) {
        if (isDebug || needLog) {
            Log.e(commonLogTag + ":" + tag, buildMessage(msg));
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     *
     * @param msg       The message you would like logged.
     * @param throwable An exception to log
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.e(commonLogTag + ":" + tag, buildMessage(msg), throwable);
        }
    }

    public static void e(String msg, Throwable throwable) {
        if (isDebug || needLog) {
            Log.e(commonLogTag, buildMessage(msg), throwable);
        }
    }

    /**
     * Building Message
     *
     * @param msg The message you would like logged.
     * @return Message String
     */
    private static String buildMessage(String msg) {

        StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[2];
        return new StringBuilder().append(caller.getClassName()).append(".")
                .append(caller.getMethodName()).append("()::").append(msg).toString();
    }

}
