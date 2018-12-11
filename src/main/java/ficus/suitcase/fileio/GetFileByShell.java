package ficus.suitcase.fileio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 工具场景：
 * 某些文件获取，是通过服务器上安装的中间件执行命令去触发
 * 涉及到服务调用生成shell命令去执行这个获取文件的操作；
 * 某些银行对账文件的获取如建行，即是通过类似这种方式来处理的
 * @author DamonFicus
 */
public class GetFileByShell {

    /**
     * tarConfigMap,serverConfigMap 这种参数获取可以自己根据实际情况通过数据库或配置中心，亦或是配置文件中去获取参数
     */
    Map<String, String> tarConfigMap = new HashMap<String, String>();

    Map<String, String> serverConfigMap = new HashMap<String, String>();

    private static final String Y="Y";

    private static final Logger logger = LoggerFactory.getLogger(GetFileByShell.class);
    private static final String ENCODING="UTF-8";


    public void getFileByShell(String ymdStr) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        if (null == ymdStr) {
            ymdStr = sdf.format(new Date());
        }

        String merNo = tarConfigMap.get("MER_NO");
        String sftpFileName = new StringBuffer(100).append("SHOP.").append(merNo).append(".").append(ymdStr).append(".KJZF").append(".txt.gz").toString();

        try {
            //建行的对账文件通过getfile客户端获取，组装并通过shell脚本的方式去执行文件下载
            boolean isFileDown = true;
            //默认为true.sh运行标识为N时应通过linux调度方式运行shell脚本获取对账文件
            //sh运行标识(Y:运行;N:不运行)
            String shRunFlag = tarConfigMap.get("SH_RUN_FLAG");
            //获取对账文件放置路径
            String rootPath = tarConfigMap.get("TAR_SAVE_PATH");
            //trsHomeKey,trsHomeVal的获取;
            Map<String, String> propertyMap = new HashMap<>(16);
            propertyMap.put("trsHomeKey", tarConfigMap.get("trsHomeKey"));
            propertyMap.put("trsHomeVal", tarConfigMap.get("trsHomeVal"));
            // 执行shell命令获取文件
            if (Y.equals(shRunFlag)) {
                try {
                    isFileDown = getFileByShell(rootPath, sftpFileName, propertyMap, ymdStr);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //通过Linux调度执行 ccbRecon.sh
            }
            if (!isFileDown) {
                logger.error("下载[{}]对账文件异常", sftpFileName);
            }

        } catch (Exception e) {

        }
    }


    /**
     * 通过sh脚本方式下载对账文件
     */
    private boolean getFileByShell(String rootPath, String remoteFile, Map<String, String> propertymap, String tradeDate)
            throws Exception {
        Process p = null;
        ProcessBuilder pb = null;
        BufferedReader reader = null;
        try {
            File file = new File(rootPath + remoteFile);
            if (file.exists()) {
                boolean del = file.delete();
                if (!del) {
                    logger.warn("无法删除文件");
                }
            }
            // 命令格式: GetFile localPath/localName remoteName
            String cmd = new StringBuilder(200).append("GetFile ").append(rootPath).append(remoteFile).append(" ")
                    .append(remoteFile).toString();
            pb = new ProcessBuilder("/bin/sh", "-c", cmd);
            pb.directory(new File(rootPath));
            Map<String, String> env = pb.environment();
            // 分析'transfile/etc/trsenv.sh'获得的值:
            String trsHomeKey = propertymap.get("trsHomeKey");
            // 设置环境变量
            if (StringUtils.isNotEmpty(env.get(trsHomeKey))) {
                // 分析'transfile/etc/trsenv.sh'获得的值:
                // $HOME/transfile
                String trsHomeVal = propertymap.get("trsHomeVal");
                env.put(trsHomeKey, trsHomeVal);
                env.put("PATH", trsHomeVal + "/bin:" + env.get("PATH"));
            }
            logger.info(String.format("执行命令下载[%s]对账文件[%s][%s]...", tradeDate, remoteFile, pb.command()));
            p = pb.start();
            if (null == p) {
                logger.error("下载[{}]文件命令执行异常[进程为null]", tradeDate);
                return false;
            }
            // 避免阻塞
            new StreamHandler(p.getInputStream(), rootPath, StreamHandler.Type.INPUT).start();
            // 避免阻塞
            new StreamHandler(p.getErrorStream(), rootPath, StreamHandler.Type.ERROR).start();
            int wf = p.waitFor();
            logger.info("下载[{}]文件命令执行完毕.", tradeDate);
            if (wf != 0) {
                logger.error("下载[{}]文件命令执行异常, waitFor值: [{}]", tradeDate, wf);
                return false;
            }
            return true;
        } finally {
            ReaderHelper.close(reader);
            try {
                OutputStream os = null == p ? null : p.getOutputStream();
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                logger.error("关闭流异常", e);
            }
        }
    }


    private static class StreamHandler extends Thread {
        private InputStream is;
        private String rootPath;
        private String fileName;

        private static enum Type {
            /**
             * 文件类型 ERROR
             */
            ERROR,
            /**
             * 文件类型 INPUT
             */
            INPUT
        }

        public StreamHandler(InputStream is, String rootPath, Type type) {
            this.is = is;
            this.rootPath = rootPath;
            this.fileName = type.name().toLowerCase();
        }
        @Override
        public void run() {
            String line = null;
            FileOutputStream out = null;
            BufferedReader reader = null;
            StringBuilder meta = new StringBuilder(10000);
            try {
                reader = ReaderHelper.getReader(is);
                while (null != (line = reader.readLine())) {
                    meta.append(line).append("\n");
                }
                if (meta.length() > 0) {
                    out = new FileOutputStream(new StringBuilder(rootPath).append("sh_").append(fileName)
                            .append(".log").toString());
                    out.write(meta.toString().getBytes(ENCODING));
                    out.flush();
                }
            } catch (Exception e) {
                logger.error("读取进程输出流异常", e);
            } finally {
                try {
                    if (null != is) {
                        is.close();
                    }
                    if (null != out) {
                        out.close();
                    }
                } catch (IOException e) {
                    logger.error("关闭流异常", e);
                }
            }
        }
    }

}
