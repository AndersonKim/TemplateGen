package bean;

import com.sun.org.apache.xml.internal.utils.StringToStringTable;

public class AppInfo {
    String appname;
    String datasource;

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname;
    }

    public String getDatasource() {
        return datasource;
    }

    public void setDatasource(String datasource) {
        this.datasource = datasource;
    }
}
