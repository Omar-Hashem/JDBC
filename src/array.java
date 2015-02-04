import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class array implements Array {

	private String arrstr;
	private String arraydatatype; // / need to be initialized from schema with
									// the type of the array
	private ArrayList<String> arrele = new ArrayList<String>();

	private void parsing(String arr) {
		arrstr = arrstr.replaceAll("[{]", "");
		arrstr = arrstr.replaceAll("[}]", "");
		arrstr = arrstr.replaceAll("'", "");
		String[] tmpp = arrstr.split(",");
		for (int i = 0; i < tmpp.length; i++) {
			this.arrele.add(tmpp[i]);
		}
		return;
	}

	public array(String arraystr, String arraydatatype) {
		if (arraystr == null)
			arraystr = "";
		this.arrstr = arraystr;
		this.arraydatatype = arraydatatype;
	}

	@Override
	public Object getArray() throws SQLException {
		Object arrayobject;
		if (arrstr.equals("") || arrstr.length() == 0)
			return "";
		parsing(arrstr);
		if (arraydatatype.toLowerCase().equals("varchar")
				|| arraydatatype.toLowerCase().equals("char")
				|| arraydatatype.toLowerCase().equals("longvarchar")) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < arrele.size(); i++) {
				ret.add(arrele.get(i));
			}
			arrayobject = ret;
			return arrayobject;
		}

		if (arraydatatype.toLowerCase().equals("tinyint")
				|| arraydatatype.toLowerCase().equals("smallint")
				|| arraydatatype.toLowerCase().equals("integer")) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < arrele.size(); i++) {
				ret.add(Integer.parseInt(arrele.get(i)));
			}
			arrayobject = ret;
			return arrayobject;
		}

		if (arraydatatype.toLowerCase().equals("real")
				|| arraydatatype.toLowerCase().equals("float")) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < arrele.size(); i++) {
				ret.add(Float.parseFloat(arrele.get(i)));
			}
			arrayobject = ret;
			return arrayobject;
		}

		if (arraydatatype.toLowerCase().equals("double")) {
			ArrayList ret = new ArrayList();
			for (int i = 0; i < arrele.size(); i++) {
				ret.add(Double.parseDouble(arrele.get(i)));
			}
			arrayobject = ret;
			return arrayobject;
		}
		return null;
	}

	@Override
	public void free() throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getArray(Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long index, int count) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getArray(long index, int count, Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getBaseType() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getBaseTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(Map<String, Class<?>> map)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long index, int count) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResultSet getResultSet(long index, int count,
			Map<String, Class<?>> map) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
