package ficus.suitcase.ftp;

import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;
import java.util.Properties;

/**
 * 类说明：
 * sftp服务器工具类 　不支持代理
 * CreateDate: 20181009
 * @author DamonFicus
 */
public class SFTPUtil {
    private final Logger logger = LoggerFactory.getLogger(SFTPUtil.class);
    private final boolean isDebug = logger.isDebugEnabled();

    private String ftpHost;
    private int ftpPort;
    private String ftpUserName;
    private String ftpPassword;
    private int timeout = 5 * 60 * 1000;

    private ChannelSftp sftp = null;
    private Session session = null;

    public SFTPUtil(String ftpHost, int ftpPort, String ftpUserName, String ftpPassword, int timeout) {
        this.ftpHost = ftpHost;
        this.ftpPassword = ftpPassword;
        this.ftpPort = ftpPort;
        this.ftpUserName = ftpUserName;
        if (timeout > 0) {
            this.timeout = timeout;
        }
    }

    /**
     * 连接sftp服务器
     * ftpHost 主机
     * ftpPort 端口
     * ftpUserName 用户名
     * ftpPassword 密码
     * timeout 超时时间
     * @return
     */
    private void connect() throws Exception {
        try {
            //创建JSch对象
            JSch jsch = new JSch();
            //根据用户名，主机ip，端口获取一个Session对象
            session = jsch.getSession(ftpUserName, ftpHost, ftpPort);
            if (isDebug) {
                logger.debug("Session created.");
            }
            if (ftpPassword != null) {
                //设置密码
                session.setPassword(ftpPassword);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            //为Session对象设置properties
            session.setConfig(config);
            //设置timeout时间
            session.setTimeout(timeout);
            //通过Session建立链接
            session.connect();
            if (isDebug) {
                logger.debug("Session connected.");
                logger.debug("Opening Channel.");
            }
            //打开SFTP通道
            Channel channel = session.openChannel("sftp");
            //建立SFTP通道的连接
            channel.connect();
            if (isDebug) {
                logger.debug("Connected successfully to ftpHost = " + ftpHost + ",as ftpUserName = " + ftpUserName
                        + ", returning: " + channel);
            }
            sftp = (ChannelSftp) channel;
        } catch (Exception ex) {
            logger.error("连接SFTP异常", ex);
            throw ex;
        }
    }

    /**
     * 方法说明：<br>
     * 关闭sftp连接
     * @throws Exception
     */
    private void closeChannel() {
        try {
            if (sftp != null) {
                sftp.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        } catch (Exception ex) {
            logger.error("关闭SFTP连接异常", ex);
        }
    }

    /**
     * 上传文件
     * @param directory  上传的目录
     * @param uploadFile 要上传的文件
     */
    public void upload(String directory, String uploadFile) throws Exception {
        InputStream inputStream = null;
        try {
            //连接sftp
            connect();
            //打开目录
            cd(sftp, directory);
            File file = new File(uploadFile);
            inputStream = new FileInputStream(file);
            sftp.put(inputStream, file.getName());
            if (isDebug) {
                logger.debug("上传成功!");
            }
        } catch (Exception ex) {
            logger.error("上传文件异常", ex);
            throw ex;
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            //关闭sftp
            closeChannel();
        }
    }

    /**
     * 下载文件
     * @param directory    下载目录
     * @param downloadFile 下载的文件
     * @param saveFile     存在本地的路径
     */
    public void download(String directory, String downloadFile, String saveFile) throws Exception {
        OutputStream outputStream = null;
        try {
            //连接sftp
            connect();
            sftp.cd(directory);
            File file = new File(saveFile);
            outputStream = new FileOutputStream(file);
            sftp.get(downloadFile, outputStream);
            if (isDebug) {
                logger.debug("下载文件成功!");
            }
            outputStream.flush();
        } catch (Exception ex) {
            logger.error("下载文件异常", ex);
            throw ex;
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            //关闭sftp
            closeChannel();
        }
    }

    /**
     * 删除文件
     * @param directory  要删除文件所在目录
     * @param deleteFile 要删除的文件
     */
    public void delete(String directory, String deleteFile) throws Exception {
        try {
            //连接sftp
            connect();
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception ex) {
            logger.error("删除文件异常", ex);
            throw ex;
        } finally {
            //关闭sftp
            closeChannel();
        }
    }

    /**
     * 方法说明：<br>
     * 判断文件/文件夹是否存在
     * @param sftp
     * @param fileDir
     * @return　false不存在
     */
    @SuppressWarnings("rawtypes")
    private boolean exists(ChannelSftp sftp, String fileDir) {
        try {
            List content = sftp.ls(fileDir);
            if (content == null) {
                return false;
            }
            return true;
        } catch (SftpException ex) {
            if (isDebug) {
                logger.debug("文件/文件夹不存在:" + ex.getMessage());
            }
            return false;
        }
    }

    /**
     * 打开目录，如不存在则创建
     * @param sftp
     * @param fileDir
     * @throws Exception
     */
    private void cd(ChannelSftp sftp, String fileDir) throws Exception {
        try {
            fileDir = fileDir.replace("\\", "/");
            String[] dirs = fileDir.split("/");
            if (dirs != null && dirs.length > 0) {
                for (int i = 0; i < dirs.length; i++) {
                    String dir = dirs[i];
                    if (StringUtils.isEmpty(dir)) {
                        continue;
                    }
                    //判断目录是否存在
                    boolean flag = exists(sftp, dir);
                    if (!flag) {
                        //创建目录
                        sftp.mkdir(dir);
                    }
                    //打开目录
                    sftp.cd(dir);
                }
            }
        } catch (Exception ex) {
            logger.error("打开目录异常", ex);
            throw ex;
        }

    }

}

 