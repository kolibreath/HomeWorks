import java.util.HashMap;

public class test {

    private void parseDetail(String source){
        for(int i= 0;i<source.length();i++){
            switch(source.charAt(i) ){
                case '<':
                    //todo 判断i+1是否可行
                    if(source.charAt(i+1) == '='){
                        //向result 中添加 <=
                        i++;
                    }
                    //直接添加<
                    break;
                case '>':
                    if(source.charAt(i+1) == '='){
                        //添加 >=
                    }
                    //直接添加 >
                    break;
                case '=':
                    if(source.charAt(i+1) == '='){
                        //添加 ==
                    }
                    //直接添加 =
                    break;
                case '!':
                    if(source.charAt(i+1) == '='){
                        //添加！=
                    }
                    //
                    break;
                case ':':
                    if(source.charAt(i+1) == '='){
                        //添加 ：=
                    }
                    //
                    break;
            }
        }
    }
}
