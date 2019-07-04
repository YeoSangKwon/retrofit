package retro.winitech.com.retrofitapi.retroModel;

import java.util.List;


/**
 * Server에서 상위 식별자 (MD팀의 경우 DATA 또는 RESULT 라는 상위식별자 아래 Array가 오도록 구현되어있음)를
 * 보내지 않고 보낼 경우 String 형식으로 단일 또는 다중으로 바로 파싱해서 사용가능
 * 이때는 서버에서 주는 값을 getter / setter 형식에 맞게 만들어 주어야함
 *
 * @see retro.winitech.com.retrofitapi.retroIF.RetroInterface
 * */
public class Contributor {

    //단일스트링
    private String current_user_url;
    public Contributor(String current_user_url){
        this.current_user_url = current_user_url;
    }

    public String getCurrent_user_url() {
        return current_user_url;
    }
    public void setCurrent_user_url(String current_user_url) {
        this.current_user_url = current_user_url;
    }

    //다중리스트
    private List<data> list;
    public List<data> getList() {
        return list;
    }
    public void setList(List<data> list) {
        this.list = list;
    }

    public class data{
        private String id;
        private String node_id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }
    }

}
