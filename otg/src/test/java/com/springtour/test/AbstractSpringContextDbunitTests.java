package com.springtour.test;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import lombok.Setter;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.*;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;

import com.springtour.otg.integration.AbstractOtgIntegrationTests;

public abstract class AbstractSpringContextDbunitTests extends
		AbstractOtgIntegrationTests {


	private static List<Connection> connections = new ArrayList<Connection>();
	protected String filePathPrefix;
	
	@Setter
	private boolean usingSchema = false;

	public AbstractSpringContextDbunitTests() {
		TestConfigurations configurations = (TestConfigurations) super
				.getApplicationContext().getBean("testConfigurations");
		this.usingSchema = configurations.isUsingSchema();
	}

	protected IDatabaseConnection getConnection() throws Exception {
		// get connection
		Connection con = getDataSource().getConnection();
		IDatabaseConnection connection = null;
		if (usingSchema) {
			connection = getConnectionUsingSchema(con);
		} else {
			connection = getConnection(con);
		}
		connections.add(con);
		return connection;
	}

	private IDatabaseConnection getConnectionUsingSchema(Connection con)
			throws SQLException, DatabaseUnitException {
		DatabaseMetaData databaseMetaData = con.getMetaData();
		// oracle schema name is the user name
		return new DatabaseConnection(con, databaseMetaData.getUserName()
				.toUpperCase());
	}

	private IDatabaseConnection getConnection(Connection con)
			throws DatabaseUnitException {
		IDatabaseConnection connection = new DatabaseConnection(con);
		return connection;
	}

	@Override
	public void onTearDown() throws Exception {
		for (Connection connection : connections) {
			if (!connection.isClosed()) {
				connection.close();
			}
		}
	}

	private DataSource getDataSource() {
		return (DataSource) super.getApplicationContext().getBean("dataSource");
	}

	protected IDataSet getDataSet(List<String> fileNames) throws Exception {
		IDataSet[] dataSetArray = new IDataSet[] {};
		List<IDataSet> dataSets = new ArrayList<IDataSet>();
		for (String fileName : fileNames) {
			File file = new File(filePath(fileName));
			dataSets.add(new XlsDataSet(file));
		}
		return new CompositeDataSet(dataSets.toArray(dataSetArray));
	}

	protected IDataSet actualDataSet(List<String[]> actualTables)
			throws Exception {
		QueryDataSet databaseSet = new QueryDataSet(getConnection());
		for (String[] table : actualTables) {
			databaseSet.addTable(table[0], table[1]);
		}
		return databaseSet;
	}

	protected IDataSet expectDataSet(List<String> expectFileNames)
			throws Exception {
		return getDataSet(expectFileNames);
	}

	protected String filePath(String fileName) {
		return filePathPrefix + fileName;
	}

	protected static final String SELECT_ALL_FROM = "select * from ";

}
