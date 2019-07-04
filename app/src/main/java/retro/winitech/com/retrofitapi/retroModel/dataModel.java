package retro.winitech.com.retrofitapi.retroModel;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Server에서 받아오는 JsonArray를 저장 하고
 * Client에서 사용하기 위한 데이터 모델 (retorifit이 gson 기반이라 gson lib를 사용함)
 *
 * @see retro.winitech.com.retrofitapi.retroIF.TestRetroIF
 * */
public class dataModel {

    //JsonObject 사용시
    public class data{
        private JsonObject RESULT = new JsonObject();
        public JsonObject getRESULT() {
            return RESULT;
        }
        public void setRESULT(JsonObject RESULT) {
            this.RESULT = RESULT;
        }
    }

    //JsonArray 사용시

    //다중리스트
    private List<dataArray> list;
    public List<dataArray> getList() {
        return list;
    }
    public void setList(List<dataArray> list) {
        this.list = list;
    }

    public class dataArray{
        private String message;
        private String pubKey;

        public String getPubKey() {
            return pubKey;
        }

        public void setPubKey(String pubKey) {
            this.pubKey = pubKey;
        }

        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

    }
}
