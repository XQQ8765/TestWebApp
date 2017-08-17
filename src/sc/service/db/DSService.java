package sc.service.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import sc.utils.AppPropertiesConfig;

public class DSService {

	private DataSource dataSource = null;
	private String dataSourceJNDIName = null;
	private Context context = null;
	private Connection conn = null;
	private String dbType = null;
	private StringBuffer resultStr = new StringBuffer("");
	private String[][] resultTable;
	public DSService(String dbType) throws NamingException{

			this.context = new InitialContext();
			this.dataSourceJNDIName = AppPropertiesConfig.getDataSourceJNDIName();
			this.dataSource = (DataSource)context.lookup(AppPropertiesConfig.getDataSourceJNDIName());
			if(dbType == null){
				this.dbType = "mysql";
			}
			else this.dbType = dbType;

	}



	public DSService(String dbType, String DSJNDIName) throws NamingException{

			this.context = new InitialContext();
			this.dataSourceJNDIName = DSJNDIName;
			this.dataSource = (DataSource)context.lookup(DSJNDIName);
			this.dbType = dbType;

	}

	public String getDataSourceJNDIName() {
		return dataSourceJNDIName;
	}

	public String getDbType() {
		return dbType;
	}

	public void getConnection() throws SQLException{
		if(this.dataSource!=null){
			this.conn = this.dataSource.getConnection();
		}
	}

	private void getConnection(String username, String password) throws SQLException{
		if(this.dataSource!=null){
			this.conn = this.dataSource.getConnection(username, password);
		}
	}

	private void closeConnection() throws SQLException{
		if(this.conn!=null){
			this.conn.close();
		}
	}

	public String[][] executeSQLPS(String sqlStr, String[] params) throws SQLException{

		this.getConnection();
		PreparedStatement sta = conn.prepareStatement(sqlStr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		//Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		for(int i =0; i<params.length; i++ ){
			sta.setString(i+1, params[i]);
		}
		ResultSet rs=sta.executeQuery();
		this.nornalizeResult(rs);
		//stmt.close();
		sta.close();
		this.closeConnection();
		return this.resultTable;
	}


	public String[][] queryLimit(String sqlStr, int limit) throws SQLException{

		this.getConnection();
		PreparedStatement sta = conn.prepareStatement(sqlStr, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		//Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		sta.setInt(1, limit);
		ResultSet rs=sta.executeQuery();
		this.nornalizeResult(rs);
		//stmt.close();
		sta.close();
		this.closeConnection();
		return this.resultTable;
	}

	public String[][] executeSQL(String sqlStr) throws SQLException{

		this.getConnection();
		Statement stmt=conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs=stmt.executeQuery(sqlStr);
		this.nornalizeResult(rs);
		stmt.close();
		this.closeConnection();
		return this.resultTable;
	}

	public void nornalizeResult(ResultSet resultSet) throws SQLException{
		resultStr.delete(0, resultStr.length());
		ResultSetMetaData metaData = resultSet.getMetaData();
		int columnCount = metaData.getColumnCount();
		resultSet.last();
		int rowCount = resultSet.getRow();
		resultSet.beforeFirst();
		this.resultTable = new String[rowCount+1][columnCount];
		int j = 0;
		int i = 0;
		for( i=1; i<=columnCount; i++){
			resultTable[j][i-1] = metaData.getColumnLabel(i);
		}
		while (resultSet.next()) {
			j++;
			for( i=1; i<=columnCount; i++){

					resultTable[j][i-1] =resultSet.getString(metaData.getColumnLabel(i));

			}

		}
	}

	public String[][] getDBTables() throws SQLException{
		String sql = this.getSQL();
		if(sql!=null){
			return this.executeSQL(sql);
		}
		else{
			return null;
		}
	}

	public String[][] getDBTablesPS() throws SQLException{

		String sql = this.getSQLPS();
		if(sql!=null){
			return this.queryLimit(sql, 50);
		}
		else return null;
	}


	public String getSQL(){
		String SQLStr = null;
		if(this.dbType!=null){
			if(this.dbType.equalsIgnoreCase("mysql")){
				SQLStr = "show tables";
				//SQLStr = "SELECT * FROM test.employee";
			}
			else if(this.dbType.equalsIgnoreCase("oracle")){
				SQLStr = "select table_name from user_tables";
			}
			else if(this.dbType.equalsIgnoreCase("sqlserver")){
				SQLStr = "select * from sys.tables";
			}
		}
		return SQLStr;
	}

	public String getSQLPS(){
		String SQLStr = null;
		if(this.dbType!=null){
			if(this.dbType.equalsIgnoreCase("mysql")){
				SQLStr = "SELECT `TABLES`.`TABLE_NAME` FROM `information_schema`.`TABLES` WHERE `TABLES`.`TABLE_TYPE` = 'base table' limit 0,?";
				//SQLStr = "SELECT * FROM test.employee";
			}
			else if(this.dbType.equalsIgnoreCase("oracle")){
				SQLStr = "select table_name from user_tables where rownum<?";
			}
			else if(this.dbType.equalsIgnoreCase("sqlserver")){
				SQLStr = "select top ? * from sys.tables";
			}
		}
		return SQLStr;
	}

}
