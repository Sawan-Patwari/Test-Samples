package test.directory.monitor;

/**
 * This is based on Builder pattern.
 * 
 * @author Sawan.Patwari
 *
 */
public class FTPDetailsBuilder {
	private String userName;
	private String password;
	private String ftpServer;
		
	public FTPDetailsBuilder setUserName(String userName) {
		this.userName = userName;
		
		return this;
	}

	public FTPDetailsBuilder setPassword(String password) {
		this.password = password;
		
		return this;
	}
	
	public FTPDetailsBuilder setFtpServer(String ftpServer) {
		this.ftpServer = ftpServer;
		
		return this;
	}
	
	public FTPDetails build() {
		return new FTPDetails(userName, password, ftpServer);
	}
}
