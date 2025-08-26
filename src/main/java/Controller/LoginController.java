package Controller;

import Entity.Member;
import Repository.loginRepository;
import Service.LoginService;
import com.example.card.loginForm;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService service;
    private Member member;

    //우선 로그인 창 가져오기
    @GetMapping("/login")
    public String logInForm(@ModelAttribute loginForm loginForm) {
        return "/login/loginForm";
    }

    //로그인
    @PostMapping("/login")
    public String LoginController(@Valid @ModelAttribute loginForm login, BindingResult bindingResult, @RequestParam(defaultValue = "/") String redirectURL, HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            return "/login/loginForm";
        }
//여기 member로 할당해서 써야 말이되는데 null오류가 있어서 다시보기
        Optional<Member> mem = service.login(login.getLoginId(), login.getPassword());
        if(mem.isEmpty()) {
            bindingResult.reject("loginFall", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "/login/loginForm";
        }
        HttpSession session = request.getSession();
        session.setAttribute("loginId", login.getLoginId());
        session.setAttribute("loginName", mem.get());
        return "redirect:" + redirectURL;
    }
    //로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        return "redirect: /";    }
}
