package Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.regex.Pattern;

@Entity
@Getter
public class Member {
    private static final int MAX_ID_LENGTH = 10;
    private static final int MAX_PASSWORD_LENGTH = 10;
    private static final int MAX_NAME_LENGTH = 20;

    private static final Pattern idd = Pattern.compile("^(?=.*[0-9])(?=.[a-z])(?=.[A-Z])[a-zA-Z0-9]*$]");
    private static final Pattern passwordd = Pattern.compile("^(?=.*[0-9])(?=.[a-z])(?=.[A-Z])[a-zA-Z0-9]*$]");
    //id랑 password 모두 영문자와 숫자조합으로 한정해 두었습니다

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private String logInId;
    private String password;
    private String name;

    public Member(String logInId, String password, String name) {
        idTest(logInId);
        this.logInId = logInId;
        passwordTest(password);
        this.password = password;
        nameTest(name);
        this.name = name;
    }

    private void idTest(String id){
        if(id.length() > MAX_ID_LENGTH){
            throw new IllegalArgumentException("ID는 "+MAX_ID_LENGTH+"자까지 가능합니다. ");
        }
        if(!idd.matcher(id).matches()){
            throw new IllegalArgumentException("ID는 알바벳과 숫자의 조합만 가능합니다.");
        }
    }

    private void passwordTest(String password){
        if(password.length() > MAX_PASSWORD_LENGTH){
            throw new IllegalArgumentException("password는 "+MAX_PASSWORD_LENGTH+"자까지 가능합니다. ");
        }
        if(!passwordd.matcher(password).matches()){
            throw new IllegalArgumentException("비밀번호는 알파벳과 숫자의 조합만 가능합니다");
        }
    }

    private void nameTest(String name){
        if(name.length() > MAX_NAME_LENGTH){
            throw new IllegalArgumentException("이름은 "+MAX_NAME_LENGTH+"자까지 자능합니다");
        }
    }
}
