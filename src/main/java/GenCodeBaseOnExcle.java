import bean.NodeInfo;
import bean.NodeInfoVo;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

public class GenCodeBaseOnExcle {
    public static String file_path = "D:\\IdeaCode\\TemplateGen\\src\\main\\resources\\nodeinfo.xlsx";
    public static List<NodeInfo> nodeInfos = new ArrayList<NodeInfo>();
    public static List<NodeInfoVo> nodeInfoVos = new ArrayList<NodeInfoVo>();

    public static void main(String[] args) {
        //1.读取excle中的数据
        readDataFromExcle();
        //2.将节点数据格式化
        formateNodeInfoVos();
        //3.根据格式生成对应的代码
        bulidCode();
    }

    private static void bulidCode() {
        StringBuffer allArray = new StringBuffer();
        allArray.append("\tvar allArray = new Array(");
        for(NodeInfoVo v:nodeInfoVos){
            StringBuffer b = new StringBuffer();
            b.append("\tvar ");
            b.append(v.getContext()+" = new Array(");
            b.append("\'"+v.getName()+"\',\n");
            for(String u:v.getNodeUrl()){
                b.append("\t\t\t\'http://"+u+":"+v.getPort()+"/"+v.getContext()+"\',\n");
            }
            b.delete(b.lastIndexOf(","),b.length());
            b.append(");\n");
            allArray.append(v.getContext()+",");
            System.out.println(b.toString());
        }
        allArray.delete(allArray.lastIndexOf(","),allArray.length());
        allArray.append(");");
        System.out.println(allArray.toString());
    }

    private static void formateNodeInfoVos() {
        for (NodeInfo n : nodeInfos) {
            NodeInfoVo v = new NodeInfoVo();
            v.setPort(n.getPort());
            v.setContext(n.getContext());
            v.setName(n.getName());
            v.setNodeUrl(new ArrayList<String>());

            //逗号分割的多个节点
            if (n.getNode().contains(",")) {
                String[] urls = n.getNode().split(",");
                for (String url : urls) {
                    //使用横线分割的多个节点
                    if (url.contains("-")) {
                        //根据ip地址规则确定url的范围
                        String base = url.substring(0, url.lastIndexOf("."));
                        String sStr = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf("-"));
                        String eStr = url.substring(url.lastIndexOf("-")+1, url.length());
                        int s = Integer.parseInt(sStr);
                        int e = Integer.parseInt(eStr);
                        for (int i = s; i < e + 1; i++) {
                            String currentNode = base + "." + i;
                            v.getNodeUrl().add(currentNode);
                        }
                    }else{
                        v.getNodeUrl().add(n.getNode());
                    }
                }
            } else {
                //使用横线分割的多个节点
                String url = n.getNode();
                //使用横线分割的多个节点
                if (url.contains("-")) {
                    //根据ip地址规则确定url的范围
                    String base = url.substring(0, url.lastIndexOf("."));
                    String sStr = url.substring(url.lastIndexOf(".")+1, url.lastIndexOf("-"));
                    String eStr = url.substring(url.lastIndexOf("-")+1, url.length());
                    int s = Integer.parseInt(sStr);
                    int e = Integer.parseInt(eStr);
                    for (int i = s; i < e + 1; i++) {
                        String currentNode = base + "." + i;
                        v.getNodeUrl().add(currentNode);
                    }
                }else{
                    v.getNodeUrl().add(n.getNode());
                }

            }
            nodeInfoVos.add(v);
        }
    }

    /**
     * 将节点信息读取到nodeinfos中
     */
    private static void readDataFromExcle() {
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream(file_path));
        nodeInfos = reader.readAll(NodeInfo.class);
    }
}
