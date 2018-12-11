package ficus.suitcase.ftp;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPConnector;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.connectors.DirectConnector;
import it.sauronsoftware.ftp4j.connectors.HTTPTunnelConnector;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Properties;

/**
 * FTP操作 支持代理(在环境变量中设置http.proxyHost和http.proxyPort)
 * @author DamonFicus
 */
public class Ftp4jUtil {

	private final Logger logger = LoggerFactory.getLogger(Ftp4jUtil.class);

	private final boolean isDebug = logger.isDebugEnabled();
	/**
	 * FTP服务地址
	 */
	private String address;
	/**
	 * FTP登录用户名
	 */
	private String userName;
	/**
	 * FTP登录密码
	 */
	private String password;
	/**
	 * FTP端口
	 */
	private int port = 21;
	
	private static final int FTP_READ_TIME_OUT = 60;

	private static final String SLASH="/";

	public Ftp4jUtil(String serverIp, String userName, String password) {
		this.address = serverIp;
		this.userName = userName;
		this.password = password;
	}

	public Ftp4jUtil(String serverIp, int port, String userName, String password) {
		this.address = serverIp;
		this.userName = userName;
		this.password = password;
		this.port = port;
	}

	/**
	 * 获取FTP客户端对象
	 * @return
	 * @throws Exception
	 */
	private FTPClient getClient() throws Exception {
		if (isDebug) {
			logger.debug("正在连接" + address + "的FTP服务器");
		}
		FTPClient client = new FTPClient();
		client.setCharset("UTF-8");
		client.setType(FTPClient.TYPE_BINARY);
		FTPConnector connector = getConnector();
		if (connector != null) {
			connector.setReadTimeout(FTP_READ_TIME_OUT);
			client.setConnector(connector);
		}
		client.connect(address, port);
		client.login(userName, password);
		if (isDebug) {
			logger.debug("连接" + address + "的FTP服务器成功");
		}
		return client;
	}

	/**
	 * 注销客户端连接
	 * @param client
	 * FTP客户端对象
	 * @throws Exception
	 */
	private void logout(FTPClient client) throws Exception {
		if (null == client) {
			return;
		}
		try {
			//退出登录(有些FTP服务器未实现此功能，若未实现则会出错)
			client.logout();
		} catch (FTPException fe) {
		} finally {
			//断开连接
			if (client.isConnected()) {
				client.disconnect(true);
			}
			if (isDebug) {
				logger.debug("已从FTP服务器" + address + "断开");
			}
		}
	}

	/**
	 * 方法说明： 获取连接器实例
	 * 代理使用 HTTPTunnelConnector 
	 * <br>默认使用DirectConnector
	 * @return
	 */
	private FTPConnector getConnector() {
		Properties prop = System.getProperties();
		// 此环境变量需要在weblogic中设置。由于hhtp.proxyHost会引起不稳定，固用https的参数表示http代理
		String proxyHost = prop.getProperty("https.proxyHost");
		String proxyPort = prop.getProperty("https.proxyPort");
		if (proxyHost != null && !"".equals(proxyHost) && proxyPort != null && !"".equals(proxyPort)) {
			HTTPTunnelConnector connector = new HTTPTunnelConnector(proxyHost, Integer.parseInt(proxyPort));
			if (isDebug) {
				logger.debug("正在通过代理服务器[{}:{}]访问FTP", proxyHost, proxyPort);
			}
			return connector;
		} else {
			if (isDebug) {
				logger.debug("未使用代理服务器访问FTP");
			}
			return new DirectConnector();
		}
	}

	/**
	 * 下载文件
	 * @param remoteFileName 远程文件
	 * @param localFileName 本地存放文件
	 * @throws Exception
	 */
	public void downloadFile(String remoteFileName, String localFileName) throws Exception {
		FTPClient client = null;
		try {
			client = getClient();
			if (isDebug) {
				logger.debug("开始从FTP服务器{}下载文件", address);
			}
			client.download(remoteFileName, new File(localFileName));
			if (isDebug) {
				logger.debug("从FTP服务器{}下载文件完成", address);
			}
		} finally {
			logout(client);
		}
	}
	
	/**
	 * 下载文件
	 * @param remoteFilePath 远程文件路径
	 * @param remoteFileName 远程文件
	 * @param localFileName 本地存放文件
	 * @throws Exception
	 */
	public void downloadFile(String remoteFilePath, String remoteFileName, String localFileName) throws Exception {
		FTPClient client = null;
		try {
			client = getClient();
			client.changeDirectory(remoteFilePath);
			if (isDebug) {
				logger.debug("开始从FTP服务器{}下载文件", address);
			}
			client.download(remoteFileName, new File(localFileName));
			if (isDebug) {
				logger.debug("从FTP服务器{}下载文件完成", address);
			}
		} finally {
			logout(client);
		}
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @throws Exception
	 */
	public void uploadFile(File file) throws Exception {
		FTPClient client = null;
		try {
			client = getClient();
			if (isDebug) {
				logger.debug("开始从FTP服务器{}上传文件", address);
			}
			client.upload(file);
			if (isDebug) {
				logger.debug("从FTP服务器{}上传文件完成", address);
			}
		} finally {
			logout(client);
		}
	}
	
	/**
	 * 上传文件
	 * @param remoteFilePath 远程文件
	 * @param localFile 本地存放文件
	 * @throws Exception
	 */
	public void uploadFile(String remoteFilePath, File localFile) throws Exception {
		FTPClient client = null;
		try {
			client = getClient();
			mkdirs(remoteFilePath,client);
			
			if (isDebug) {
				logger.debug("开始从FTP服务器{}上传文件", address);
			}
			client.upload(localFile);
			if (isDebug) {
				logger.debug("从FTP服务器{}上传文件完成", address);
			}
		} finally {
			logout(client);
		}
	}
	
	/** 
     * 创建目录 
     * @param ftpClient FTP客户端对象
     * @param dir 目录 
     * @throws Exception
     */  
    private void mkdirs(String dir, FTPClient ftpClient) throws Exception {
        if (null == dir || "".equals(dir)) {  
            return;
        }  
        dir = formatPath4FTP(dir);
        String[] dirs = dir.split(SLASH);
        for (int i = 0; i < dirs.length; i++) {  
            dir = dirs[i];  
            if (!StringUtils.isEmpty(dir)) {
                if (!exists(dir,ftpClient)) {  
                	ftpClient.createDirectory(dir);  
                }  
                ftpClient.changeDirectory(dir);  
            }  
        }  
    }  
    
    /**
     * 格式化文件路径，将其中不规范的分隔转换为标准的分隔符
     * 并且去掉末尾的"/"符号(适用于FTP远程文件路径或者Web资源的相对路径)。
     * @param path 文件路径
     * @return 格式化后的文件路径
     */
    private String formatPath4FTP(String path) {
            String reg0 = "\\\\+";
            String reg = "\\\\+|/+";
            String temp = path.trim().replaceAll(reg0, SLASH);
            temp = temp.replaceAll(reg, SLASH);
            if (temp.length() > 1 && temp.endsWith(SLASH)) {
                    temp = temp.substring(0, temp.length() - 1);
            }
            return temp;
    }
    
    /** 
     * 判断文件或目录是否存在 
     * @param dir 文件或目录
     * @return 
     * @throws Exception
     */  
    private boolean exists(String dir, FTPClient ftpClient) throws Exception {
        return getFileType(dir,ftpClient) != -1;  
    }  
    
    /** 
     * 判断当前为文件还是目录 
     * @param dir 文件或目录
     * @return -1、文件或目录不存在 0、文件 1、目录 
     * @throws Exception
     */  
    private int getFileType(String dir, FTPClient ftpClient) throws Exception {
        FTPFile[] files = null;  
        try {  
            files = ftpClient.list(dir);  
        } catch (Exception e) {
            return -1;  
        }  
        if (files.length > 1) {  
            return FTPFile.TYPE_DIRECTORY;  
        } else if (files.length == 1) {  
            FTPFile f = files[0];  
            if (f.getType() == FTPFile.TYPE_DIRECTORY) {  
                return FTPFile.TYPE_DIRECTORY;  
            }  
            String path = dir + SLASH + f.getName();
            try {  
                int len = ftpClient.list(path).length;  
                if (len == 1) {  
                    return FTPFile.TYPE_DIRECTORY;  
                } else {  
                    return FTPFile.TYPE_FILE;  
                }  
            } catch (Exception e) {
                return FTPFile.TYPE_FILE;  
            }  
        } else {  
            try {  
            	ftpClient.changeDirectory(dir);  
            	ftpClient.changeDirectoryUp();  
                return FTPFile.TYPE_DIRECTORY;  
            } catch (Exception e) {
                return -1;  
            }  
        }  
    }  


	/**
	 * 方法说明：<br>
	 * @param remoteFileDir
	 * @param remoteFileName
	 * @param localFileName
	 */
	public void downloadFiles(String remoteFileDir, String remoteFileName,
                              String localFileName) {
		
		FTPClient client = null;
			
		logger.error("进入批量FTP对账文件下载");
		try {
			try {
				client = getClient();
				client.changeDirectory(remoteFileDir);
				FTPFile[] fs = client.list();
				
				for(FTPFile f : fs )
				{
					remoteFileName = f.getName();
					if(0 == f.getType())
					{
						client.download(remoteFileName, new File(localFileName+remoteFileName));
					}
				}
			} catch (Exception e) {
				logger.error("批量FTP对账文件下载异常", e);
			}
		} finally {
			logger.error("批量FTP对账文件下载");
		}
	}
}
