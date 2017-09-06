package telesketch.lmsierra.com.telesketch.drawingUtils;

import java.util.ArrayList;
import java.util.List;

public class UserLines {

    private List <UserLine> userLines;

    public UserLines(){
    }

    public List<UserLine> getAllLines(){

        if(userLines == null){
            userLines = new ArrayList<>();
        }

        return userLines;
    }

    public void add(UserLine userLine){
        getAllLines().add(userLine);
    }

    public void add(UserLines userLines){
        getAllLines().addAll(userLines.getAllLines());
    }

    public UserLine getCurrent(){
        return getAllLines().get(count() - 1);
    }

    public UserLine get(int position){
        return getAllLines().get(position);
    }

    public void removeAll(){
        getAllLines().clear();
    }

    public void remove(int position){
        getAllLines().remove(position);
    }

    public int count(){
        return getAllLines().size();
    }
}
