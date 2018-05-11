package com.yilaole.utils;

/**
 * 数据返回code解释 util
 */

public class DebugResultUtil {

    /**
     * code =200,成功！
     *
     * @param code
     * @return
     */

    public static String GetReturnException(int code) {
        StringBuffer buffer = new StringBuffer();
        switch (code) {
            case 100:
                buffer.append("code = 100,Continue");
                break;
            case 101:
                buffer.append("code = 101,Switching Protocols");
                break;
            case 102:
                buffer.append("code = 102,Processing");
                break;
            case 200:
                buffer.append("code = 200,OK");
                break;
            case 201:
                buffer.append("code = 201,Created");

                break;
            case 202:
                buffer.append("code = 202,Accepted");

                break;
            case 203:
                buffer.append("code = 203,Non-Authoritative Information");

                break;
            case 204:
                buffer.append("code = 204,No Content");

                break;
            case 205:
                buffer.append("code = 205,Reset Content");

                break;
            case 206:
                buffer.append("code = 206,Partial Content");

                break;
            case 207:
                buffer.append("code = 207,Multi-Status");

                break;
            case 208:
                buffer.append("code = 208,Already Reported");

                break;
            case 226:
                buffer.append("code = 226,IM Used");

                break;
            case 300:
                buffer.append("code = 300,Multiple Choices");

                break;
            case 301:
                buffer.append("code = 301,Moved Permanently");

                break;
            case 302:
                buffer.append("code = 302,Found");

                break;
            case 303:
                buffer.append("code = 303,See Other");

                break;
            case 304:
                buffer.append("code = 304,Not Modified");

                break;
            case 305:
                buffer.append("code = 305,Use Proxy");

                break;
            case 307:
                buffer.append("code = 307,Temporary Redirect");

                break;
            case 308:
                buffer.append("code = 308,Permanent Redirect");

                break;
            case 400:
                buffer.append("code = 400,Bad Request");

                break;
            case 401:
                buffer.append("code = 401,Unauthorized");

                break;
            case 402:
                buffer.append("code = 402,Payment Required");

                break;
            case 403:
                buffer.append("code = 403,Forbidden");

                break;
            case 404:
                buffer.append("code = 404,Not Found");

                break;
            case 405:
                buffer.append("code = 405,Method Not Allowed");

                break;
            case 406:
                buffer.append("code = 406,Not Acceptable");

                break;
            case 407:
                buffer.append("code = 407,Proxy Authentication Required");

                break;
            case 408:
                buffer.append("code = 408,Request Timeout");

                break;
            case 409:
                buffer.append("code = 409,Conflict");

                break;
            case 410:
                buffer.append("code = 410,Gone");

                break;
            case 411:
                buffer.append("code = 411,Length Required");

                break;
            case 412:
                buffer.append("code = 412,Precondition Failed");

                break;
            case 413:
                buffer.append("code = 413,Payload Too Large");

                break;
            case 414:
                buffer.append("code = 414,URI Too Long");

                break;
            case 415:
                buffer.append("code = 415,Unsupported Media Type");

                break;
            case 416:
                buffer.append("code = 416,Range Not Satisfiable");

                break;
            case 417:
                buffer.append("code = 417,Expectation Failed");

                break;
            case 418:
                buffer.append("code = 418, \t'm a teapot");

                break;
            case 421:
                buffer.append("code = 421,Misdirected Request");

                break;
            case 422:
                buffer.append("code = 422,Unprocessable Entity");

                break;
            case 423:
                buffer.append("code = 423,Locked");

                break;
            case 424:
                buffer.append("code = 424,Failed Dependency");

                break;
            case 425:
                buffer.append("code = 425,Reserved for WebDAV advanced collections expired proposal");

                break;
            case 426:
                buffer.append("code = 426,Upgrade Required");

                break;
            case 428:
                buffer.append("code = 428,Precondition Required");

                break;
            case 429:
                buffer.append("code = 429,Too Many Requests");

                break;
            case 431:
                buffer.append("code = 431,Request Header Fields Too Large");

                break;
            case 451:
                buffer.append("code = 451,Unavailable For Legal Reasons");

                break;
            case 500:
                buffer.append("code = 500,Internal Server Error");

                break;
            case 501:
                buffer.append("code = 501,Not Implemented");

                break;
            case 502:
                buffer.append("code = 502,Bad Gateway'");

                break;
            case 503:
                buffer.append("code = 503,Service Unavailable");

                break;
            case 504:
                buffer.append("code = 504,Gateway Timeout");

                break;
            case 505:
                buffer.append("code = 505,HTTP Version Not Supported");

                break;
            case 506:
                buffer.append("code = 506,Variant Also Negotiates (Experimental)");

                break;
            case 507:
                buffer.append("code = 507,Insufficient Storage");

                break;
            case 508:
                buffer.append("code = 508,Loop Detected");

                break;
            case 510:
                buffer.append("code = 510,Not Extended");

                break;
            case 511:
                buffer.append("code = 511,Network Authentication Required");

                break;
            default:
                buffer.append("没找到该code对应的异常code=" + code);

                break;
        }
        return buffer.toString();
    }
}