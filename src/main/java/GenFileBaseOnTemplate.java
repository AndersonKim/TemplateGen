import bean.AppInfo;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 根据模板批量生成对应的jsp文件
 */
public class GenFileBaseOnTemplate {

    public static String file_in_path="D:\\IdeaCode\\TemplateGen\\src\\main\\resources\\appname_datasource.xlsx";
    public static String file_template_path="D:\\IdeaCode\\TemplateGen\\src\\main\\resources\\data_source_control.jsp";
    public static Map<String,String> app_datasource = new HashMap<String, String>();
    public static void main(String[] args){
        String a = "aosidjaisjd";
        for(String s:a.split(",")){
            System.out.println(s);
        }
    }
    public static void main1(String[] args){
        //1.读取数据源关联关系
        readAppNameAndDataSource();
        //2.按照模板生成文件
        for(String key:app_datasource.keySet()){
            String appName=(String)key;
            String dataSource =app_datasource.get(appName);
            buildFileBaseOnTemplate(file_template_path,appName,dataSource);
        }
    }
    /**
     * 根据模板生成对应的文件
     * @param template
     */
    private static void buildFileBaseOnTemplate(String template,String appName,String datasource) {
        FileReader fileReader = new FileReader(template);
        String result = fileReader.readString();
        result=result.replace("String jndiNameStr = \"\";","String jndiNameStr = \""+datasource+"\";");

        String filePath ="C:\\Users\\Administrator\\Desktop\\2020年7月29日-系统监控方案\\增量文件包\\各个应用中的jsp页面\\"+appName+"\\data_source_control.jsp";
        FileUtil.touch(filePath);
        FileWriter writer = new FileWriter(filePath);
        writer.write(result);
    }

    /**
     * 读取文件中的应用以及数据源的关系
     */
    private static void readAppNameAndDataSource() {
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream(file_in_path));
        List<AppInfo> all = reader.readAll(AppInfo.class);
        for(AppInfo m:all){
            app_datasource.put(m.getAppname(),m.getDatasource());
        }
    }
}
