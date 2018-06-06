package favouritePackage.designPatterns;

/**
 * This is an immutable class. Immutable pattern approach.
 * 
 * @author Sawan.Patwari
 */
public class FTPDetails {
	
	private String userName;
	private String password;
	private String ftpServer;
	
	public FTPDetails(String userName, String password, String ftpServer) {
		this.userName = userName;
		this.password = password;
		this.ftpServer = ftpServer;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String getFtpServer() {
		return ftpServer;
	}

}
