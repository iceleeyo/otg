package com.springtour.test;

import java.io.*;
import java.sql.*;
import java.util.*;

import javax.sql.*;

import lombok.*;

import org.dbunit.*;
import org.dbunit.database.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.excel.*;

import com.springtour.otg.integration.*;

public abstract class AbstractPersistenceTestsUsingDbunitTemplate<Aggregate, Identity>
		extends AbstractOtgIntegrationTests {
	protected static final String SELECT_ALL_FROM = "select * from ";

	private List<Connection> connections = new ArrayList<Connection>();
	@Setter
	private boolean usingSchema = false;

	public AbstractPersistenceTestsUsingDbunitTemplate() {
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

	protected IDataSet getXlsDataSetFrom(String fileName) throws Exception {
		InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream(fileName);
		return new XlsDataSet(inputStream);
	}

	protected IDataSet getActualSaved() throws Exception {
		return getQueryDataSet(getCriteriasForSaved());
	}

	protected IDataSet getActualUpdated() throws Exception {
		return getQueryDataSet(getCriteriasForUpdated());
	}

	protected IDataSet getQueryDataSet(QueryDataSetCriteria... criterias)
			throws Exception {
		QueryDataSet dataSet = new QueryDataSet(getConnection());
		for (QueryDataSetCriteria criteria : criterias) {
			dataSet.addTable(criteria.getTableName(), criteria.getQuery());
		}
		return dataSet;
	}

	protected IDataSet getExpectedSaved() throws Exception {
		return getXlsDataSetFrom(getXlsOfExpectForSaved());
	}

	protected IDataSet getExpectedUpdated() throws Exception {
		return getXlsDataSetFrom(getXlsOfExpectForUpdated());
	}

	protected IDataSet getPrototypeOfSaved() throws Exception {
		return getXlsDataSetFrom(getXlsOfPrototypeForSaved());
	}

	protected IDataSet getPrototypeOfUpdated() throws Exception {
		return getXlsDataSetFrom(getXlsOfPrototypeForUpdated());
	}

	protected abstract String getXlsOfExpectForSaved();

	protected abstract String getXlsOfExpectForUpdated();

	protected abstract String getXlsOfPrototypeForSaved();

	protected abstract String getXlsOfPrototypeForUpdated();

	protected abstract QueryDataSetCriteria[] getCriteriasForSaved();

	protected abstract QueryDataSetCriteria[] getCriteriasForUpdated();

	protected abstract Aggregate findPrototypeForSaved();

	protected abstract Aggregate copyForSaving(Aggregate prototype);

	protected abstract void doSave(Aggregate saved);

	protected abstract Aggregate findPrototypeForUpdated();

	protected abstract void copyForUpdating(Aggregate target,
			Aggregate prototype);

	protected abstract void doUpdate(Aggregate updated);

	protected void doTestSaving() throws Exception {
		Aggregate prototype = findPrototypeForSaved();

		doSave(copyForSaving(prototype));

		assertSaved();
	}

	protected void assertSaved() throws DatabaseUnitException, Exception {
		Assertion.assertEquals(getExpectedSaved(), getActualSaved());
	}

	protected void doTestUpdating(Aggregate target) throws Exception {
		Aggregate prototype = findPrototypeForUpdated();

		copyForUpdating(target, prototype);

		doUpdate(target);

		assertUpdated();
	}

	protected void assertUpdated() throws DatabaseUnitException, Exception {
		Assertion.assertEquals(getExpectedUpdated(), getActualUpdated());
	}

	protected static class QueryDataSetCriteria {
		private String tableName;
		private String query;

		public QueryDataSetCriteria(String tableName, String query) {
			this.tableName = tableName;
			this.query = query;
		}

		public static QueryDataSetCriteria as(String tableName, String query) {
			return new QueryDataSetCriteria(tableName, query);
		}

		public String getTableName() {
			return tableName;
		}

		public String getQuery() {
			return query;
		}
	}

}
