package com.jl.sample;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    List<String> Mlist = new ArrayList<>();
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
//        ArrayList<String> mycopy=new ArrayList<String>(Arrays.asList(new String[Mlist.size()]));
//        Collections.copy(mycopy, Mlist);
//        mycopy.add("a");
//        mycopy.add("b");
//        mycopy.add("c");
//        getMyList(mycopy);

        List<String> s=new ArrayList<String>();
        List<String> rs=new ArrayList<String>();
        s.add("a");
        s.add("b");
        s.add("c");
        pl(s,rs);
    }

    private List<String> getMyList(List<String> list){
        List<String> my = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {

            if (list.size()>2) {
                ArrayList<String> mycopy=new ArrayList<String>(Arrays.asList(new String[list.size()]));
                Collections.copy(mycopy, list);
                my.add(list.get(i));
                mycopy.remove(i);
                System.out.println(getMyList(mycopy).toString());
                return getMyList(mycopy);
            }else if (list.size()==2){

                //System.out.println(list.toString());
                my.addAll(list);
                return my;
            }
        }
        return list;
    }



    public static void pl(List<String> s,List rs){

        if(s.size()==1) {

            rs.add(s.get(0));
            System.out.println(rs.toString());
            rs.remove(rs.size()-1);

        }else{

            for(int i=0;i<s.size();i++){

                rs.add(s.get(i));
                List<String> tmp=new ArrayList<String>();
                for(String a:s)
                    tmp.add(a);
                tmp.remove(i);
                pl(tmp,rs);
                rs.remove(rs.size()-1);

            }
        }
    }



}