package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;


//리턴값이 html파일이 아니고 데이터(보통 json으로 변환해서던져줌)
@RestController
public class DummyControllerTest {
    @Autowired //DummyControllerTest가 메모리에 뜰때 얘도 같이 뜬다
    //의존성주입(DI)
    private UserRepository userRepository;
    //http://localhost:8000/blog/dummy/join(요청)
    //http의 body에 username, password, email데이터를 가지고(요청)
    //@RequestParam("username",p~~)요거 생략

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 존재하지 않습니다.";
        }

        return "삭제되었습니다. id : " + id;
    }

    @Transactional
    //더티체킹
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requesrUser){
        //json데이터요청->java Object(MessageConverter의 Jackson라이브러리가 변환해서 받아준다)
        System.out.println("id: "+ id);
        System.out.println("password: "+ requesrUser.getPassword());
        System.out.println("email: "+ requesrUser.getEmail());

//        requesrUser.setId(id);
//        requesrUser.setUsername("ssar");
//        userRepository.save(requesrUser);

        //save함수는 아이디없거나 id있는데 데이터베이스에 없으면 insert
        //데이터베이스에 id값이 있다면 업데이트로 쳐준다
        //근데 다른값이 null로 변해서 업데이트시 save잘안쓴다.
        //그래서 save로 업데이트하려면 유저를 먼저 찾고 그걸 세이브하면됨

        //orElseThrow() -> 못찾으면 실행할거
        //자바는 파라미터에 함수를 못넣는다. 자바스크립트나 dart는 넣을수있다
        //근데 람다식으로 함수날리면됨
        //여기서 영속화됨
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });
        user.setPassword(requesrUser.getPassword());
        user.setEmail(requesrUser.getEmail());

//        userRepository.save(user);
//        더티체킹
        return user;
    }
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한 페이지당 2건의 데이터를 리턴받을메서드
    //http://localhost:8000/blog/dummy/user 이게
    //http://localhost:8000/blog/dummy/user?page=0 이거와 같다
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        //두개씩, 아이디 최신순으로 정렬해서
        Page<User> pagingUser = userRepository.findAll(pageable);
        //.getContent()
        //분기처리도 가능하다
//        if(pagingUser.isFirst()){
//
//        }
//        처리가 다 끝나면 결과는 클래스와 같은 형식으로 받는게좋다
        List<User> users = pagingUser.getContent();
        return users;
    }

    //{id}주소로 파라미터를 전달받을수있다
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        //user4 호출시 못찾으면 값이 null이 되서 에러발생하니까
        //Optional로 User객체 감싸서 가져올거니 null인지 아닌지 판단해서 return
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 유저는 존재하지 않습니다.id:"+id);
            }
        });
//                orElseGet(new Supplier<User>() {
//            @Override
//            public User get() {
//                return new User(); //빈 객체를 user에 넣어줌
//            }
//        });
        //요청 - 웹브라우자
        //user객체 = 자바 오브젝트
        //웹브라우저 이해못하니 변환 ->json (Gson라이브러리)
        //스프링부트 = 메세지컨버터가 응답시 자동작동
        //만약 자바오브젝트를 리턴하게되면 메세지컨버터가 jackson라이브러리를 호출해서
        //user오브젝트를 json으로 변환해서 브라우저에게 던져준다
        return user;
    }

    //pom태그로 받아옴
    @PostMapping("/dummy/join")
    public String join(User user){ //key=value (약속된규칙)
        System.out.println("id = "+user.getId());
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());
        System.out.println("role = "+user.getRole());
        System.out.println("createDate = "+user.getCreateDate());

        user.setRole(RoleType.USER); //디폴트값 회원가입시 넣어준다
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}