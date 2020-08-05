package bean;

import java.util.List;

public class NodeInfoVo extends NodeInfo{
    List<String> nodeUrl;

    public List<String> getNodeUrl() {
        return nodeUrl;
    }

    public void setNodeUrl(List<String> nodeUrl) {
        this.nodeUrl = nodeUrl;
    }
}
