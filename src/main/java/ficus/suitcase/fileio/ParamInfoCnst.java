package ficus.suitcase.fileio;

import java.util.Arrays;
import java.util.List;

/**
 * Created by DamonFicus on 2018/10/29.
 * @author DamonFicus
 */
public final class ParamInfoCnst {
    public static final String PAYOUT_WAY_MANUAL = "HAND";
    public static final String PAYOUT_WAY_AUTO = "AUTO";
    public static final String PAY_BATCH_SWITCH_ON = "ON";
    public static final String PAY_BATCH_SWITCH_OFF = "OFF";
    public static final String LOCAL_DIR = "LOCAL_DIR";
    public static final String FTP_DIR = "FTP_DIR";
    public static final String FTP_IP = "FTP_IP";
    public static final String FTP_PORT = "FTP_PORT";
    public static final String FTP_PASSWORD = "FTP_PASSWORD";
    public static final String FTP_USERNAME = "FTP_USERNAME";
    public static final String FTP_TIMEOUT = "FTP_TIMEOUT";
    public static final String SFTP_DIR = "SFTP_DIR";
    public static final String SFTP_IP = "SFTP_IP";
    public static final String SFTP_PORT = "SFTP_PORT";
    public static final String SFTP_PASSWORD = "SFTP_PASSWORD";
    public static final String SFTP_USERNAME = "SFTP_USERNAME";
    public static final String SFTP_TIMEOUT = "SFTP_TIMEOUT";
    public static final String[] SFTP_ARRAY = new String[]{"LOCAL_DIR", "SFTP_DIR", "SFTP_IP", "SFTP_PORT", "SFTP_PASSWORD", "SFTP_USERNAME", "SFTP_TIMEOUT"};
    public static final List<String> SFTP_PARAM;
    public static final Integer UPDATE_BATCH_MAXNUM;
    public static final String FILE_MODULENO_GW = "1";
    public static final String FILE_MODULENO_CORE = "2";
    public static final String FILE_MODULENO_FRONT = "3";
    public static final String FILE_DIRECTION_UPLOAD = "1";
    public static final String FILE_DIRECTION_DOWNLOAD = "2";
    public static final String FILE_FILETYPE_REC = "1";
    public static final String FILE_FILETYPE_RTN = "2";
    public static final int FILE_NUMBER_PER_WRITE = 100;
    public static final int FILE_PAGESIZE = 300;
    public static final String SOURCE_TYPE_FILE = "1";
    public static final String SOURCE_TYPE_RECORD = "2";
    public static final String SOURCE_TYPE_WRITE = "3";

    static {
        SFTP_PARAM = Arrays.asList(SFTP_ARRAY);
        UPDATE_BATCH_MAXNUM = Integer.valueOf(100);
    }

    public ParamInfoCnst() {
    }
}
